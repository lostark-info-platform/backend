package org.info.lostark.auth.presentation

import org.info.lostark.auth.command.application.AuthService
import org.info.lostark.auth.command.application.dto.TokenResponse
import org.info.lostark.auth.presentation.dto.TokenRefreshRequest
import org.info.lostark.common.presentation.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthRestController(
    private val authService: AuthService
) {
    @PostMapping("/refresh")
    fun generateTokenByRefreshToken(@RequestBody request: TokenRefreshRequest): ApiResponse<TokenResponse> {
        return ApiResponse.success(authService.generateTokenByRefreshToken(request.refreshToken))
    }
}