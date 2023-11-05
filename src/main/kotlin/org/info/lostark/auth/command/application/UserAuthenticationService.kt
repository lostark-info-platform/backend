package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.application.dto.JwtTokenCommandResponse
import org.info.lostark.auth.command.application.dto.RegisterUserCommand
import org.info.lostark.common.security.JwtTokenProvider
import org.info.lostark.user.command.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserAuthenticationService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService
) {
    fun generateTokenByRegister(command: RegisterUserCommand): JwtTokenCommandResponse {
        check(userRepository.findByEmail(command.email) == null) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        val user = userRepository.save(command.toEntity())
        return generate(user)
    }

    fun generateTokenByLogin(email: String, password: Password): JwtTokenCommandResponse {
        val user = userRepository.findByEmail(email)
            ?: throw UnidentifiedUserException("사용자 정보가 일치하지 않습니다.")
        user.authenticate(password)
        return generate(user)
    }

    fun generateTokenByRefreshToken(refreshToken: String): JwtTokenCommandResponse {
        val user = refreshTokenService.getUser(refreshToken)
        return generate(user)
    }

    private fun generate(user: User): JwtTokenCommandResponse {
        val token = jwtTokenProvider.createToken(user.email)
        val refreshToken = refreshTokenService.create(user.id)
        return JwtTokenCommandResponse(token, refreshToken)
    }
}

