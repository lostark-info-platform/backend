package org.info.lostark.auth.command.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.auth.command.domain.OAuth2Provider
import org.info.lostark.support.afterRootTest

class OAuth2StrategyResolverTest : BehaviorSpec({
    val kakaoOAuth2Strategy = mockk<KakaoOAuth2Strategy>()
    val googleOAuth2Strategy = mockk<GoogleOAuth2Strategy>()

    val oAuth2StrategyResolver = OAuth2StrategyResolver(listOf(kakaoOAuth2Strategy, googleOAuth2Strategy))

    Given("지원하는 프로바이더가 존재하는 경우") {
        val provider = "KAKAO"
        every { kakaoOAuth2Strategy.provider } returns OAuth2Provider.KAKAO
        every { googleOAuth2Strategy.provider } returns OAuth2Provider.GOOGLE

        When("프로바이더 로그인 전략을 찾으면") {
            val actual = oAuth2StrategyResolver.resolve(provider)

            Then("정상 응답한다") {
                actual.shouldNotBeNull()
            }
        }
    }

    Given("지원하는 프로바이더가 존재하지 않는 경우") {
        val provider = "facebook"
        every { kakaoOAuth2Strategy.provider } returns OAuth2Provider.KAKAO
        every { googleOAuth2Strategy.provider } returns OAuth2Provider.GOOGLE

        When("프로바이더 로그인 전략을 찾으면") {
            Then("정상 응답한다") {
                shouldThrow<IllegalArgumentException> { oAuth2StrategyResolver.resolve(provider) }
            }
        }
    }

    Given("지원하는 프로바이더를 소문자로 입력한 경우") {
        val lowerCaseProvider = "google"
        every { kakaoOAuth2Strategy.provider } returns OAuth2Provider.KAKAO
        every { googleOAuth2Strategy.provider } returns OAuth2Provider.GOOGLE

        When("프로바이더 로그인 전략을 찾으면") {
            val actual = oAuth2StrategyResolver.resolve(lowerCaseProvider)

            Then("대문자로 전환 후 정상 응답한다") {
                actual.shouldNotBeNull()
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
