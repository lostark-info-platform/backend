package org.info.lostark.ui.api

import org.info.lostark.application.AuthenticateUserRequest
import org.info.lostark.application.JwtTokenResponse
import org.info.lostark.application.LogoutRequest
import org.info.lostark.application.RefreshTokenService
import org.info.lostark.application.RegisterUserRequest
import org.info.lostark.application.ResignRequest
import org.info.lostark.application.ResignService
import org.info.lostark.application.TokenRefreshRequest
import org.info.lostark.application.UserAuthenticationService
import org.info.lostark.application.UserResponse
import org.info.lostark.domain.user.User
import org.info.lostark.security.LoginUser
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
    fun generateToken(@RequestBody request: RegisterUserRequest): ResponseEntity<ApiResponse<JwtTokenResponse>> {
        val token = userAuthenticationService.generateTokenByRegister(request)
        return ResponseEntity.ok(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun generateToken(@RequestBody request: AuthenticateUserRequest): ResponseEntity<ApiResponse<JwtTokenResponse>> {
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