package org.info.lostark.ui.api.user

import jakarta.validation.Valid
import org.info.lostark.application.user.AuthenticateUserRequest
import org.info.lostark.application.auth.JwtTokenResponse
import org.info.lostark.application.user.LogoutRequest
import org.info.lostark.application.auth.RefreshTokenService
import org.info.lostark.application.user.RegisterUserRequest
import org.info.lostark.application.user.ResignRequest
import org.info.lostark.application.auth.ResignService
import org.info.lostark.application.user.TokenRefreshRequest
import org.info.lostark.application.auth.UserAuthenticationService
import org.info.lostark.application.user.UserResponse
import org.info.lostark.domain.user.User
import org.info.lostark.security.LoginUser
import org.info.lostark.ui.api.common.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserRestController(
    private val userAuthenticationService: UserAuthenticationService,
    private val refreshTokenService: RefreshTokenService,
    private val resignService: ResignService
) {
    @PostMapping("/register")
    fun generateToken(@RequestBody @Valid request: RegisterUserRequest): ResponseEntity<ApiResponse<JwtTokenResponse>> {
        val token = userAuthenticationService.generateTokenByRegister(request)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun generateToken(@RequestBody @Valid request: AuthenticateUserRequest): ResponseEntity<ApiResponse<JwtTokenResponse>> {
        val token = userAuthenticationService.generateTokenByLogin(request)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/refresh")
    fun generateToken(@RequestBody request: TokenRefreshRequest): ResponseEntity<ApiResponse<JwtTokenResponse>> {
        val token = userAuthenticationService.generateTokenByRefreshToken(request)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/logout")
    fun revokeToken(
        @RequestBody request: LogoutRequest,
        @LoginUser user: User
    ): ResponseEntity<Unit> {
        refreshTokenService.revoke(request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/resign")
    fun resign(
        @RequestBody request: ResignRequest,
        @LoginUser user: User
    ): ResponseEntity<Unit> {
        resignService.resign(user.id, request)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    fun me(@LoginUser user: User): ApiResponse<UserResponse> {
        return ApiResponse.success(UserResponse(user))
    }
}