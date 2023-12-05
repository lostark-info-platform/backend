package org.info.lostark.auth.infra.oauth2.config

import org.info.lostark.auth.infra.oauth2.google.client.GoogleApiClient
import org.info.lostark.auth.infra.oauth2.kakao.client.KakaoApiClient
import org.info.lostark.common.config.AbstractHttpInterfaceConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthHttpInterfaceConfig : AbstractHttpInterfaceConfig() {
    @Bean
    fun kakaoApiClient(): KakaoApiClient {
        return createHttpInterface(KakaoApiClient::class.java)
    }

    @Bean
    fun googleApiClient(): GoogleApiClient {
        return createHttpInterface(GoogleApiClient::class.java)
    }
}