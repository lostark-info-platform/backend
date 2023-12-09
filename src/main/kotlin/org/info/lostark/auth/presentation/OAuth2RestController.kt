package org.info.lostark.auth.presentation

import jakarta.servlet.http.HttpServletResponse
import org.info.lostark.auth.command.application.OAuth2Service
import org.info.lostark.auth.command.application.dto.TokenResponse
import org.info.lostark.auth.presentation.dto.OAuth2LoginRequest
import org.info.lostark.common.application.StaticConfigService
import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.user.command.domain.SocialProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/oauth2")
class OAuth2RestController(
    private val oAuth2Service: OAuth2Service,
    private val staticConfigService: StaticConfigService
) {
    @GetMapping("/code/{providerString}")
    fun redirectGetAuthCodePage(
        @PathVariable providerString: String,
        state: String?,
        response: HttpServletResponse
    ) {
        val targetUrl = oAuth2Service.getRedirectUrl(SocialProvider.of(providerString), state)
        response.sendRedirect(targetUrl)
    }

    @GetMapping("/callback/{providerString}")
    fun redirectCodeAndStateToClient(
        @PathVariable providerString: String,
        code: String,
        state: String?,
        response: HttpServletResponse
    ) {
        response.sendRedirect(getTargetUrl(code, state, providerString))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: OAuth2LoginRequest): ApiResponse<TokenResponse> {
        return ApiResponse.success(oAuth2Service.login(request.toCommand()))
    }

    private fun getTargetUrl(code: String, state: String?, providerString: String) =
        "${staticConfigService.get().clientBaseUrl}?code=$code&state=${state ?: ""}&provider=$providerString"
}