package org.info.lostark.domain.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.nulls.shouldNotBeNull
import org.info.lostark.application.RegisterUserRequest
import org.info.lostark.application.UserAuthenticationService
import org.info.lostark.fixture.createUser
import org.info.lostark.support.IntegrationTest

@IntegrationTest
class UserAuthenticationIntegrationTest(
    private val userAuthenticationService: UserAuthenticationService,
    private val userRepository: UserRepository
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

            Then("JWT를 응답한다") {
                actual.token.shouldNotBeNull()
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
})