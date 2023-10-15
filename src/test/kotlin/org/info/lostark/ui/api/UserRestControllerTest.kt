package org.info.lostark.ui.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.application.*
import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.UnidentifiedUserException
import org.info.lostark.fixture.createUser
import org.info.lostark.support.bearer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(UserRestController::class)
class UserRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var userAuthenticationService: UserAuthenticationService

    @MockkBean
    private lateinit var refreshTokenService: RefreshTokenService

    @MockkBean
    private lateinit var resignService: ResignService

    @Test
    fun `유효한 회원가입 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        every { userAuthenticationService.generateTokenByRegister(any()) } returns response

        mockMvc.post("/api/users/register") {
            jsonContent(RegisterUserRequest("user", "user@email.com", Password("password"), Password("password")))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-register-post"))
        }
    }

    @Test
    fun `회원가입 요청시 이미 존재하는 이메일일 시 400 BadRequest 응답한다`() {
        every { userAuthenticationService.generateTokenByRegister(any()) } throws IllegalArgumentException("이미 존재하는 이메일입니다.")

        mockMvc.post("/api/users/register") {
            jsonContent(RegisterUserRequest("user", "exist@email.com", Password("password"), Password("password")))
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            handle(document("user-register-post-already-exist-email"))
        }
    }

    @Test
    fun `유효한 로그인 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        every { userAuthenticationService.generateTokenByLogin(any()) } returns response

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest("user@email.com", Password("password")))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-login-post"))
        }
    }

    @Test
    fun `로그인 시 이메일형식이 올바르지 않은 경우 에러 발생한다`() {
        val response = createJwtResponse()
        val invalidEmail = "invalid_email"
        every { userAuthenticationService.generateTokenByLogin(any()) } returns response

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest(invalidEmail, Password("password")))
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            handle(document("user-login-post-invalid-email-form"))
        }
    }

    @Test
    fun `로그인 요청한 정보가 올바르지 않은 경우 403 Forbidden 반환한다`() {
        every { userAuthenticationService.generateTokenByLogin(any()) } throws UnidentifiedUserException("사용자 정보가 일치하지 않습니다.")

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest("unmatched@email.com", Password("password")))
        }.andExpect {
            status { isForbidden() }
        }.andDo {
            handle(document("user-login-post-forbidden"))
        }
    }

    @Test
    fun `회원이 자신의 정보를 조회한다`() {
        val response = UserResponse(createUser())

        mockMvc.get("/api/users/me") {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-me-get"))
        }
    }

    @Test
    fun `유효하지 않은 토큰으로 개인정보 요청 시 401 Unauthorized 반환한다`() {
        mockMvc.get("/api/users/me") {
            header(HttpHeaders.AUTHORIZATION, "invalid_token")
        }.andExpect {
            status { isUnauthorized() }
            content { error("로그인 정보가 정확하지 않습니다.") }
        }.andDo {
            handle(document("user-me-get-unauthorized"))
        }
    }

    @Test
    fun `유효한 리프레시 토큰으로 로그인 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        val request = TokenRefreshRequest("valid_refreshToken")

        every { userAuthenticationService.generateTokenByRefreshToken(request) } returns response
        mockMvc.post("/api/users/refresh") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-refresh-post"))
        }
    }

    @Test
    fun `로그아웃 요청시 NoContent를 반환한다`() {
        val request = LogoutRequest("refresh_token")
        every { refreshTokenService.revoke(request) } just Runs

        mockMvc.post("/api/users/logout") {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isNoContent() }
        }.andDo {
            handle(document("user-logout-post"))
        }
    }

    @Test
    fun `회원탈퇴 요청시 NoContent를 반환한다`() {
        val request = ResignRequest(Password("password"))
        every { resignService.resign(any(), any()) } just Runs

        mockMvc.post("/api/users/resign") {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isNoContent() }
        }.andDo {
            handle(document("user-resign-post"))
        }
    }

    private fun createJwtResponse(): JwtTokenResponse {
        return JwtTokenResponse(
            "valid_token",
            "refresh_token"
        )
    }
}
