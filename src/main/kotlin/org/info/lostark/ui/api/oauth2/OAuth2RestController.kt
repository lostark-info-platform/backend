package org.info.lostark.ui.api.oauth2

import org.info.lostark.application.auth.JwtTokenResponse
import org.info.lostark.application.oauth2.OAuth2LoginRequest
import org.info.lostark.application.oauth2.OAuth2Service
import org.info.lostark.ui.api.common.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/oauth2")
class OAuth2RestController(
    private val oAuth2Service: OAuth2Service
) {
    @PostMapping("/login")
    fun login(@RequestBody request: OAuth2LoginRequest): ApiResponse<JwtTokenResponse> {
        return ApiResponse.success(oAuth2Service.login(request))
    }
}