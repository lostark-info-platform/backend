package org.info.lostark.ui.api

import com.ninjasquad.springmockk.MockkBean
import org.info.lostark.application.RefreshTokenService
import org.info.lostark.application.ResignService
import org.info.lostark.application.UserAuthenticationService
import org.info.lostark.application.UserResponse
import org.info.lostark.fixture.createUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.get

@WebMvcTest(UserRestController::class)
class UserRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var userAuthenticationService: UserAuthenticationService

    @MockkBean
    private lateinit var refreshTokenService: RefreshTokenService

    @MockkBean
    private lateinit var resignService: ResignService

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
}

fun MockHttpServletRequestDsl.bearer(token: String) {
    header(HttpHeaders.AUTHORIZATION, bearerToken(token))
}

private fun bearerToken(token: String): String = "Bearer $token"