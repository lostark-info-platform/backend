package org.info.lostark.auth.command.domain

import org.springframework.stereotype.Component

@Component
class OAuth2AuthCodeUrlProviderContext(
    strategies: Set<OAuth2AuthCodeUrlProviderStrategy>
) {
    private val strategies: Map<SocialProvider, OAuth2AuthCodeUrlProviderStrategy> =
        strategies.associateBy { it.support }

    fun getRedirectUrl(provider: SocialProvider, state: String?): String {
        return strategies
            .getValue(provider)
            .provide(state)
    }
}

