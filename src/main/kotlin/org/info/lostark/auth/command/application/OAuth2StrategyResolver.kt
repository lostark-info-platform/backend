package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.domain.SocialProvider
import org.springframework.stereotype.Component

@Component
class OAuth2StrategyResolver(
    private val strategies: List<OAuth2Strategy>
) {
    fun resolve(socialProvider: SocialProvider): OAuth2Strategy {
        return strategies.first { it.provider == socialProvider }
    }
}