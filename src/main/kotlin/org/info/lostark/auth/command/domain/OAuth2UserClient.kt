package org.info.lostark.auth.command.domain

fun interface OAuth2UserClient {
    fun fetch(code: String): OAuth2User
}