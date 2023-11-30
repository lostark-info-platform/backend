package org.info.lostark.common.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import org.info.lostark.user.command.domain.SocialProvider
import org.info.lostark.user.command.domain.User

class BaseEntitiesTest : StringSpec({
    "BaseRootEntity의 id가 같다면 동일한 엔티티이다" {
        val user1 = User(
            socialUid = "social_uid",
            socialProvider = SocialProvider.GOOGLE,
            id = 1L
        )
        val user2 = User(
            socialUid = "social_uid",
            socialProvider = SocialProvider.KAKAO,
            id = 1L
        )
        val userSet = mutableSetOf(user1, user2)
        user1 shouldBeEqual user2
        userSet.size shouldBe 1
    }

    "BaseRootEntity의 id가 다르다면 동일한 엔티티가 아니다" {
        val user1 = User(
            socialUid = "social_uid",
            socialProvider = SocialProvider.GOOGLE,
            id = 1L
        )
        val user2 = User(
            socialUid = "social_uid",
            socialProvider = SocialProvider.GOOGLE,
            id = 2L
        )
        val userSet = mutableSetOf(user1, user2)
        user1 shouldNotBeEqual user2
        userSet.size shouldBe 2
    }
})