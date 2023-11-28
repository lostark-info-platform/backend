package org.info.lostark.auth.infra.oauth2.google.client

import org.info.lostark.auth.infra.oauth2.google.dto.GoogleToken
import org.info.lostark.auth.infra.oauth2.google.dto.GoogleUserResponse
import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

interface GoogleApiClient {
    @PostExchange("https://oauth2.googleapis.com/token")
    fun fetchToken(@RequestParam params: MultiValueMap<String, String>): GoogleToken

    @GetExchange("https://www.googleapis.com/oauth2/v2/userinfo")
    fun fetchUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) accessToken: String): GoogleUserResponse
}