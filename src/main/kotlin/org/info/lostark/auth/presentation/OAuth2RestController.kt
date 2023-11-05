package org.info.lostark.auth.presentation

import org.info.lostark.auth.command.application.OAuth2Service
import org.info.lostark.auth.command.application.dto.JwtTokenCommandResponse
import org.info.lostark.auth.presentation.dto.OAuth2LoginRequest
import org.info.lostark.common.presentation.ApiResponse
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
    fun login(@RequestBody request: OAuth2LoginRequest): ApiResponse<JwtTokenCommandResponse> {
        return ApiResponse.success(oAuth2Service.login(request.provider, request.accessToken))
    }
}