package org.info.lostark.auth.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.auth.command.application.AuthService
import org.info.lostark.auth.presentation.dto.TokenRefreshRequest
import org.info.lostark.fixture.createTokenResponse
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.security.WithMockCustomUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthRestController::class)
class AuthRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun `리프레시 토큰을 통해 액세시토큰을 발급한다`() {
        val response = createTokenResponse()
        every { authService.generateTokenByRefreshToken(any()) } returns response
        mockMvc.post("/api/auth/refresh") {
            jsonContent(TokenRefreshRequest("valid_refresh_token"))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("auth-refresh-post"))
        }
    }

    @Test
    @WithMockCustomUser
    fun `액세스토큰을 통해 로그아웃 한다`() {
        every { authService.logout(any()) } just Runs

        mockMvc.post("/api/auth/logout")
            .andExpect {
                status { isOk() }
            }.andDo {
                handle(document("auth-logout-post"))
            }
    }
}
