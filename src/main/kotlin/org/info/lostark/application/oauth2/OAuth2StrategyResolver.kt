package org.info.lostark.application.oauth2

import org.springframework.stereotype.Component

@Component
class OAuth2StrategyResolver(
    private val strategies: List<OAuth2Strategy>
) {
    fun resolve(provider: String): OAuth2Strategy {
        return strategies
            .find { it.provider.value == provider }
            ?: throw IllegalArgumentException("지원하지 않는 프로바이더입니다.")
    }
}