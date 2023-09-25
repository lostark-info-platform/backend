package org.info.lostark.domain.user

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldHaveSize
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
                createUser("홍길동", "gildong@email.com"),
                createUser("이순신", "sunsin@email.com")
            )
        )
        expect("모든 유저를 조회한다") {
            val actual = userRepository.findAll()
            actual shouldHaveSize 2
        }
    }
})