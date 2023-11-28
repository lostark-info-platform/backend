package org.info.lostark.common.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import org.info.lostark.auth.command.domain.OAuth2Id
import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.SocialProvider
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.User

class BaseEntitiesTest : StringSpec({
    "BaseRootEntity의 id가 같다면 동일한 엔티티이다" {
        val user1 = User(
            name = "홍길동",
            email = "gildong@email.com",
            password = Password("password"),
            id = 1L
        )
        val user2 = User(
            name = "이순신",
            email = "sunsin@email.com",
            password = Password("password"),
            id = 1L
        )
        val userSet = mutableSetOf(user1, user2)
        user1 shouldBeEqual user2
        userSet.size shouldBe 1
    }

    "BaseRootEntity의 id가 다르다면 동일한 엔티티가 아니다" {
        val user1 = User(
            name = "홍길동",
            email = "gildong@email.com",
            password = Password("password"),
            id = 1L
        )
        val user2 = User(
            name = "홍길동",
            email = "gildong@email.com",
            password = Password("password"),
            id = 2L
        )
        val userSet = mutableSetOf(user1, user2)
        user1 shouldNotBeEqual user2
        userSet.size shouldBe 2
    }

    "BaseEntity의 id가 같다면 동일한 객체이다" {
        val oAuth2User1 = OAuth2User(
            OAuth2Id("provider_user_id", SocialProvider.KAKAO),
            "user@email.com",
            "nickname",
            1L
        )
        val oAuth2User2 = OAuth2User(
            OAuth2Id("provider_user_id", SocialProvider.KAKAO),
            "user@email.com",
            "nickname",
            1L
        )
        val entitySet = mutableSetOf(oAuth2User1, oAuth2User2)
        oAuth2User1 shouldBeEqual oAuth2User2
        entitySet.size shouldBe 1
    }

    "BaseEntity의 id가 다르다면 동일한 객체가 아니다" {
        val oAuth2User1 = OAuth2User(
            OAuth2Id("provider_user_id", SocialProvider.KAKAO),
            "user@email.com",
            "nickname",
            1L
        )
        val oAuth2User2 = OAuth2User(
            OAuth2Id("provider_user_id", SocialProvider.GOOGLE),
            "user@email.com",
            "nickname",
            2L
        )
        val entitySet = mutableSetOf(oAuth2User1, oAuth2User2)
        oAuth2User1 shouldNotBeEqual oAuth2User2
        entitySet.size shouldBe 2
    }
})