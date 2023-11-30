package org.info.lostark.user.command.domain

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.nulls.shouldNotBeNull
import org.info.lostark.fixture.createUser
import org.info.lostark.support.RepositoryTest

@RepositoryTest
class UserRepositoryTest(
    private val userRepository: UserRepository
) : ExpectSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    context("회원 조회") {
        userRepository.saveAll(
            listOf(
                createUser("google_social_uid", SocialProvider.GOOGLE),
                createUser("kakao_social_uid", SocialProvider.KAKAO)
            )
        )
        expect("소셜 uid와 소셜 프로바이더를 통해 유저를 조회한다") {
            val actual = userRepository.findBySocialUidAndSocialProvider("google_social_uid", SocialProvider.GOOGLE)
            actual.shouldNotBeNull()
        }
    }
})