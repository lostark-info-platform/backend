package org.info.lostark.security

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.application.UserService
import org.info.lostark.domain.user.User
import org.info.lostark.fixture.createUser
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.context.request.NativeWebRequest

class LoginUserResolverTest : StringSpec({
    val jwtTokenProvider = mockk<JwtTokenProvider>()
    val userService = mockk<UserService>()
    val methodParameter = mockk<MethodParameter>()
    val webRequest = mockk<NativeWebRequest>()

    val loginUserResolver = LoginUserResolver(jwtTokenProvider, userService)

    class TestAuthController {
        fun user(@LoginUser user: User) {}
        fun guest(user: User) {}
    }

    "주어진 함수가 @LoginUser를 지원하는지 확인한다" {
        listOf("user" to true, "guest" to false).forAll { (methodName, expected) ->
            val method = TestAuthController::class.java.getDeclaredMethod(methodName, User::class.java)
            val loginUserParameter = MethodParameter.forExecutable(method, 0)

            loginUserResolver.supportsParameter(loginUserParameter) shouldBe expected
        }
    }

    "요청의 Authorization Header로 유저 정보를 반환한다" {
        every { jwtTokenProvider.isValidToken("valid_token") } returns true
        every { jwtTokenProvider.getSubject("valid_token") } returns "user@email.com"
        every { webRequest.getHeader(AUTHORIZATION) } returns "Bearer valid_token"
        every { userService.getByEmail("user@email.com") } returns createUser()

        val actual = loginUserResolver.resolveArgument(methodParameter, null, webRequest, null)
        actual shouldBeEqualToComparingFields createUser()
    }

    "요청의 Authorization Header가 올바르지 않을 경우 예외 발생한다" {
        listOf("Bearersdbbb", "", "Bearer").forAll { header ->
            every { webRequest.getHeader(AUTHORIZATION) } returns header

            shouldThrow<LoginFailedException> {
                loginUserResolver.resolveArgument(methodParameter, null, webRequest, null)
            }
        }
    }

    "요청의 Authorization Header가 없을 경우 예외 발생한다" {
        every { webRequest.getHeader(AUTHORIZATION) } returns null

        shouldThrow<LoginFailedException> {
            loginUserResolver.resolveArgument(methodParameter, null, webRequest, null)
        }
    }

    "요청의 Authorization Header에서 추출한 Token이 유효하지 않을 경우 예외발생한다" {
        every { webRequest.getHeader(AUTHORIZATION) } returns "invalid_token"
        every { jwtTokenProvider.isValidToken("invalid_token") } returns false

        shouldThrow<LoginFailedException> {
            loginUserResolver.resolveArgument(methodParameter, null, webRequest, null)
        }
    }
})
