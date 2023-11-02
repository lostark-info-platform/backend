package org.info.lostark.domain.user

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import org.info.lostark.domain.common.BaseRootEntity

@Entity
class User(
    @Embedded
    val information: UserInformation,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    var password: Password,
    id: Long = 0L
) : BaseRootEntity<User>(id) {
    constructor(
        name: String,
        email: String,
        password: Password,
        id: Long = 0L
    ) : this(
        UserInformation(name, email), password, id
    )

    val name: String
        get() = information.name

    val email: String
        get() = information.email

    fun authenticate(password: Password) {
        identify(this.password == password) { "사용자 정보가 일치하지 않습니다." }
    }


    fun resign(password: Password) {
        identify(this.password == password) { "사용자 정보가 일치하지 않습니다." }
        // TODO: 사용자정보 null or 빈문자열 처리
    }

    private fun identify(value: Boolean, lazyMessage: () -> Any = {}) {
        if (!value) {
            val message = lazyMessage()
            throw UnidentifiedUserException(message.toString())
        }
    }
}