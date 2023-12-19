package org.info.lostark.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "smilegate")
data class SmileGateProperties(
    val accessToken: String
)