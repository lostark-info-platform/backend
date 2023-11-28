package org.info.lostark.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Value("\${oauth2.code.client-redirect-url}")
    lateinit var clientRedirectBaseUrl: String
}