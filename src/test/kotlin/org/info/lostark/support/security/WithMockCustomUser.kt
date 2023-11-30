package org.info.lostark.support.security

import org.info.lostark.user.command.domain.SocialProvider
import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithCustomSecurityContextFactory::class)
annotation class WithMockCustomUser(
    val socialUid: String = "social_uid",
    val socialProvider: SocialProvider = SocialProvider.GOOGLE
)
