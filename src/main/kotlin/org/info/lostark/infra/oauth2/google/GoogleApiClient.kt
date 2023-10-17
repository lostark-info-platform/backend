package org.info.lostark.infra.oauth2.google

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

interface GoogleApiClient {
    @GetExchange("https://www.googleapis.com/oauth2/v2/userinfo")
    fun fetchUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) accessToken: String): GoogleUserResponse
}