package org.info.lostark.auth.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.info.lostark.auth.command.application.OAuth2Service
import org.info.lostark.auth.presentation.dto.OAuth2LoginRequest
import org.info.lostark.common.application.StaticConfigService
import org.info.lostark.fixture.createStaticConfig
import org.info.lostark.fixture.createTokenResponse
import org.info.lostark.support.RestControllerTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(OAuth2RestController::class)
class OAuth2RestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var oAuth2Service: OAuth2Service

    @MockkBean
    private lateinit var staticConfigService: StaticConfigService

    @Test
    fun `프로바이더에서 제공하는 소셜 로그인 뷰로 redirect한다`() {
        every { oAuth2Service.getRedirectUrl(any(), any()) } returns "https://accounts.google.login"

        mockMvc.get("/oauth2/code/{providerString}?state={state}", "kakao", "http://localhost:3000/custom") {
        }.andExpect {
            status { is3xxRedirection() }
        }.andDo {
            handle(document("oauth2-code-redirect"))
        }
    }

    @Test
    fun `소셜 로그인이 성공하면 서버 콜백 api를 호출하고 성공 시 클라이언트로 코드와함께 리다이렉트한다`() {
        every { staticConfigService.get() } returns createStaticConfig()

        mockMvc.get(
            "/oauth2/callback/{providerString}?code={code}&state={state}",
            "kakao",
            "authorization_code",
            "http://localhost:3000/custom"
        ) {
        }.andExpect {
            status { is3xxRedirection() }
        }.andDo {
            handle(document("oauth2-code-callback-redirect-to-client"))
        }
    }


    @Test
    fun `프로바이더와 액세스토큰이 유요한 경우 토큰을 응답한다`() {
        val response = createTokenResponse()
        val request = OAuth2LoginRequest("kakao", "authorization_code")
        every { oAuth2Service.login(request.toCommand()) } returns response
        mockMvc.post("/oauth2/login") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("oauth2-login-post"))
        }
    }
}