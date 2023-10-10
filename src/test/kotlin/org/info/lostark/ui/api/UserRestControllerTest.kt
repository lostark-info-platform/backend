package org.info.lostark.ui.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.info.lostark.application.AuthenticateUserRequest
import org.info.lostark.application.JwtTokenResponse
import org.info.lostark.application.RefreshTokenService
import org.info.lostark.application.RegisterUserRequest
import org.info.lostark.application.ResignService
import org.info.lostark.application.UserAuthenticationService
import org.info.lostark.application.UserResponse
import org.info.lostark.domain.user.Password
import org.info.lostark.fixture.createUser
import org.info.lostark.support.bearer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
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
    fun `유효한 로그인 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        every { userAuthenticationService.generateTokenByLogin(any()) } returns response

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest("user", Password("password")))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-login-post"))
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

    private fun createJwtResponse(): JwtTokenResponse {
        return JwtTokenResponse(
            "valid_token",
            "refresh_token"
        )
    }
}
