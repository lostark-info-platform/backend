package org.info.lostark.domain.user

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe

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
})