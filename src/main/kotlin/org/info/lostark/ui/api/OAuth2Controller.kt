package org.info.lostark.ui.api

import org.info.lostark.application.JwtTokenResponse
import org.info.lostark.application.oauth2.OAuth2LoginRequest
import org.info.lostark.application.oauth2.OAuth2Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/oauth2")
class OAuth2Controller(
    private val oAuth2Service: OAuth2Service
) {
    @PostMapping
    fun login(@RequestBody request: OAuth2LoginRequest): ApiResponse<JwtTokenResponse> {
        return ApiResponse.success(oAuth2Service.login(request))
    }
}