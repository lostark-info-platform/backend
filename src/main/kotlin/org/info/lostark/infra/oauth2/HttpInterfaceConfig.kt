package org.info.lostark.infra.oauth2

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

    private fun <T> createHttpInterface(clazz: Class<T>): T {
        val webClient = WebClient.create()
        val build = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient)).build()
        return build.createClient(clazz)
    }
}