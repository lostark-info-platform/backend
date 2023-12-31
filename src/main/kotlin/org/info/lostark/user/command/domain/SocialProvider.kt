package org.info.lostark.user.command.domain

enum class SocialProvider {
    KAKAO,
    GOOGLE
    ;

    companion object {
        fun of(lowerCase: String): SocialProvider {
            return SocialProvider.values().find { it.name == lowerCase.uppercase() }
                ?: throw IllegalArgumentException("Not supported Provider")
        }
    }
}