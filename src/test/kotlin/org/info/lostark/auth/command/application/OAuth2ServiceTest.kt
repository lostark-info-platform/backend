package org.info.lostark.auth.command.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.info.lostark.auth.command.application.dto.OAuth2LoginCommand
import org.info.lostark.auth.command.domain.OAuth2AuthCodeUrlProviderContext
import org.info.lostark.auth.command.domain.OAuth2UserRepository
import org.info.lostark.auth.command.domain.SocialProvider
import org.info.lostark.auth.command.domain.findByOAuth2Id
import org.info.lostark.common.security.JwtTokenProvider
import org.info.lostark.fixture.createOAuth2User
import org.info.lostark.fixture.createUser
import org.info.lostark.support.afterRootTest
import org.info.lostark.user.command.domain.UserRepository

class OAuth2ServiceTest : BehaviorSpec({
    val oAuth2Resolver = mockk<OAuth2StrategyResolver>()
    val oAuth2UserRepository = mockk<OAuth2UserRepository>()
    val userRepository = mockk<UserRepository>()
    val jwtTokenProvider = mockk<JwtTokenProvider>()
    val refreshTokenService = mockk<RefreshTokenService>()
    val oAuth2AuthCodeUrlProviderContext = mockk<OAuth2AuthCodeUrlProviderContext>()

    val oAuth2Service =
        OAuth2Service(
            oAuth2Resolver,
            oAuth2UserRepository,
            userRepository,
            jwtTokenProvider,
            refreshTokenService,
            oAuth2AuthCodeUrlProviderContext
        )

    Given("이미 OAuth2User가 저장되어 있는 경우") {
        val oAuth2User = createOAuth2User(user = createUser())
        val kakaoOAuth2Strategy = mockk<KakaoOAuth2Strategy>()
        every { oAuth2Resolver.resolve(any()) } returns kakaoOAuth2Strategy
        every { kakaoOAuth2Strategy.getOAuth2User(any()) } returns oAuth2User
        every { oAuth2UserRepository.findByOAuth2Id(oAuth2User.oAuth2Id) } returns oAuth2User
        every { jwtTokenProvider.createToken(any()) } returns "valid_token"
        every { refreshTokenService.create(any()) } returns "refresh_token"

        When("로그인 요청하면") {
            val actual = oAuth2Service.login(OAuth2LoginCommand(SocialProvider.KAKAO, "authorization_code"))

            Then("저장된 OAuth2User를 통해 엑세스토큰을 응답한다") {
                actual.token shouldBe "valid_token"
            }
        }
    }

    Given("최초 로그인인 경우") {
        val oAuth2User = createOAuth2User()
        val kakaoOAuth2Strategy = mockk<KakaoOAuth2Strategy>()
        every { oAuth2Resolver.resolve(any()) } returns kakaoOAuth2Strategy
        every { kakaoOAuth2Strategy.getOAuth2User(any()) } returns oAuth2User
        every { oAuth2UserRepository.findByProviderUserIdAndProvider(any(), any()) } returns null
        every { jwtTokenProvider.createToken(any()) } returns "valid_token"
        every { refreshTokenService.create(any()) } returns "refresh_token"
        every { userRepository.save(any()) } returns createUser()
        every { oAuth2UserRepository.save(any()) } returns oAuth2User

        When("로그인 요청하면") {
            val actual = oAuth2Service.login(OAuth2LoginCommand(SocialProvider.KAKAO, "authorization_code"))

            Then("oAuth2User와 user를 저장하면서 엑세스토큰을 응답한다") {
                actual.token shouldBe "valid_token"
                verify(exactly = 1) { oAuth2UserRepository.save(any()) }
                verify(exactly = 1) { userRepository.save(any()) }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
