package org.info.lostark.auth.infra.oauth2.config

import org.info.lostark.auth.infra.oauth2.google.client.GoogleApiClient
import org.info.lostark.auth.infra.oauth2.kakao.client.KakaoApiClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class HttpInterfaceConfig {
    @Bean
    fun kakaoApiClient(): KakaoApiClient {
        return createHttpInterface(KakaoApiClient::class.java)
    }

    @Bean
    fun googleApiClient(): GoogleApiClient {
        return createHttpInterface(GoogleApiClient::class.java)
    }

    private fun <T> createHttpInterface(clazz: Class<T>): T {
        val webClient = WebClient.create()
        val build = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient)).build()
        return build.createClient(clazz)
    }
}