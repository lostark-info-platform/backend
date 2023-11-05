package org.info.lostark.auth.command.domain

fun interface OAuth2UserClient {
    fun fetch(accessToken: String): OAuth2User
}