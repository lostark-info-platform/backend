package org.info.lostark.auth.presentation

import jakarta.validation.Valid
import org.info.lostark.auth.command.application.RefreshTokenService
import org.info.lostark.auth.command.application.ResignService
import org.info.lostark.auth.command.application.UserAuthenticationService
import org.info.lostark.auth.command.application.dto.JwtTokenCommandResponse
import org.info.lostark.auth.presentation.dto.*
import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.common.security.LoginUser
import org.info.lostark.user.command.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserAuthRestController(
    private val userAuthenticationService: UserAuthenticationService,
    private val refreshTokenService: RefreshTokenService,
    private val resignService: ResignService
) {
    @PostMapping("/register")
    fun generateToken(@RequestBody @Valid request: RegisterUserRequest): ResponseEntity<ApiResponse<JwtTokenCommandResponse>> {
        val token = userAuthenticationService.generateTokenByRegister(request.toCommand())
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun generateToken(@RequestBody @Valid request: AuthenticateUserRequest): ResponseEntity<ApiResponse<JwtTokenCommandResponse>> {
        val token = userAuthenticationService.generateTokenByLogin(request.email, request.password)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/refresh")
    fun generateToken(@RequestBody request: TokenRefreshRequest): ResponseEntity<ApiResponse<JwtTokenCommandResponse>> {
        val token = userAuthenticationService.generateTokenByRefreshToken(request.refreshToken)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/logout")
    fun revokeToken(
        @RequestBody request: LogoutRequest,
        @LoginUser user: User
    ): ResponseEntity<Unit> {
        refreshTokenService.revoke(request.refreshToken)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/resign")
    fun resign(
        @RequestBody request: ResignRequest,
        @LoginUser user: User
    ): ResponseEntity<Unit> {
        resignService.resign(user.id, request.password)
        return ResponseEntity.noContent().build()
    }
}