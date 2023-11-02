package org.info.lostark.ui.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.info.lostark.application.oauth2.OAuth2LoginRequest
import org.info.lostark.application.oauth2.OAuth2Service
import org.info.lostark.fixture.createJwtResponse
import org.info.lostark.ui.api.oauth2.OAuth2RestController
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.post

@WebMvcTest(OAuth2RestController::class)
class OAuth2RestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var oAuth2Service: OAuth2Service

    @Test
    fun `프로바이더와 액세스토큰이 유요한 경우 토큰을 응답한다`() {
        val response = createJwtResponse()
        val request = OAuth2LoginRequest("supported_provider", "access_token")

        every { oAuth2Service.login(request) } returns response

        mockMvc.post("/api/oauth2/login") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("oauth2-login-post"))
        }
    }

    @Test
    fun `프로바이더가 유요하지 않은 경우 에러 발생한다`() {
        val request = OAuth2LoginRequest("unsupported_provider", "access_token")

        every { oAuth2Service.login(any()) } throws IllegalArgumentException("지원하지 않는 프로바이더입니다.")

        mockMvc.post("/api/oauth2/login") {
            jsonContent(request)
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            handle(document("oauth2-login-post-unsupported-provider"))
        }
    }
}