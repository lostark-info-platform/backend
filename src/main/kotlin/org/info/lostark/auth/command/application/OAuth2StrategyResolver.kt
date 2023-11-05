package org.info.lostark.auth.command.application

import org.springframework.stereotype.Component

@Component
class OAuth2StrategyResolver(
    private val strategies: List<OAuth2Strategy>
) {
    fun resolve(provider: String): OAuth2Strategy {
        return strategies
            .find { it.provider.name == provider.uppercase() }
            ?: throw IllegalArgumentException("지원하지 않는 프로바이더입니다.")
    }
}