package org.info.lostark.application

import org.info.lostark.domain.user.UnidentifiedUserException
import org.info.lostark.domain.user.User
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.findByEmail
import org.info.lostark.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserAuthenticationService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService
) {
    fun generateTokenByRegister(request: RegisterUserRequest): JwtTokenResponse {
        check(userRepository.findByEmail(request.email) == null) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        val user = userRepository.save(request.toEntity())
        return generate(user)
    }

    fun generateTokenByLogin(request: AuthenticateUserRequest): JwtTokenResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UnidentifiedUserException("사용자 정보가 일치하지 않습니다.")
        user.authenticate(request.password)
        return generate(user)
    }

    fun generateTokenByRefreshToken(request: TokenRefreshRequest): JwtTokenResponse {
        val user = refreshTokenService.getUser(request.refreshToken)
        return generate(user)
    }

    private fun generate(user: User): JwtTokenResponse {
        val token = jwtTokenProvider.createToken(user.email)
        val refreshToken = refreshTokenService.create(user.id)
        return JwtTokenResponse(token, refreshToken)
    }
}

