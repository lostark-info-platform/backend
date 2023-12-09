package org.info.lostark.fixture

import org.info.lostark.common.domain.StaticConfig

private const val CLIENT_BASE_URL = "http://localhost:3000"

fun createStaticConfig(
    clientBaseUrl: String = CLIENT_BASE_URL
): StaticConfig {
    return StaticConfig(
        clientBaseUrl
    )
}