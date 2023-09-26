package org.info.lostark.domain.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldBeUUID
import java.util.UUID
import org.info.lostark.application.AuthenticateUserRequest
import org.info.lostark.application.RegisterUserRequest
import org.info.lostark.application.TokenRefreshRequest
import org.info.lostark.application.UserAuthenticationService
import org.info.lostark.fixture.createUser
import org.info.lostark.support.IntegrationTest

@IntegrationTest
class UserAuthenticationIntegrationTest(
    private val userAuthenticationService: UserAuthenticationService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) : BehaviorSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    Given("유저정보가 없는 경우") {
        val request = RegisterUserRequest(
            "홍길동1",
            "gildong1@email.com",
            Password("password"),
            Password("password")
        )

        When("회원가입 하면") {
            val actual = userAuthenticationService.generateTokenByRegister(request)

            Then("인증토큰과 리프레쉬토큰을 응답한다") {
                actual.token.shouldNotBeNull()
                actual.refreshToken.shouldBeUUID()
            }
        }
    }

    Given("이메일이 이미 존재하는 경우") {
        val email = "exist@email.com"
        userRepository.save(createUser(email = email))
        val request = RegisterUserRequest(
            "홍길동1",
            email,
            Password("password"),
            Password("password")
        )

        When("회원가입 하면") {
            Then("에러 발생한다") {
                shouldThrow<IllegalArgumentException> { userAuthenticationService.generateTokenByRegister(request) }
            }
        }
    }

    Given("유저정보가 존재하는 경우") {
        val user = createUser(email = "user@email.com", password = Password("userPassword"))
        userRepository.save(user)

        When("올바른 이메일로 로그인 하면") {
            val actual = userAuthenticationService.generateTokenByLogin(
                AuthenticateUserRequest(user.email, user.password)
            )

            Then("인증토큰과 리프레쉬토큰을 응답한다") {
                actual.token.shouldNotBeNull()
                actual.refreshToken.shouldBeUUID()
            }
        }

        When("올바르지 않은 이메일로 로그인 하면") {
            Then("에러 발생한다") {
                shouldThrow<UnidentifiedUserException> {
                    userAuthenticationService.generateTokenByLogin(
                        AuthenticateUserRequest("another@email.com", user.password)
                    )
                }
            }
        }

        When("올바르지 않은 비밀번호로 로그인 하면") {
            Then("에러 발생한다") {
                shouldThrow<UnidentifiedUserException> {
                    userAuthenticationService.generateTokenByLogin(
                        AuthenticateUserRequest(user.email, Password("anotherPassword"))
                    )
                }
            }
        }
    }

    Given("리프레쉬토큰이 존재하는 경우") {
        val refreshToken = RefreshToken(UUID.randomUUID().toString(), 3L)
        refreshTokenRepository.save(refreshToken)

        When("리프레쉬토큰을 통해 로그인하면") {
            val actual = userAuthenticationService.generateTokenByRefreshToken(
                TokenRefreshRequest(refreshToken.id)
            )

            Then("인증토큰과 리프레쉬토큰을 응답한다") {
                actual.token.shouldNotBeNull()
                actual.refreshToken.shouldBeUUID()
            }
        }
    }
})