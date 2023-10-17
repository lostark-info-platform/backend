package org.info.lostark.infra.oauth2.kakao

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

interface KakaoApiClient {
    @GetExchange(url = "https://kapi.kakao.com/v2/user/me")
    fun fetchUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) accessToken: String): KakaoUserResponse
}