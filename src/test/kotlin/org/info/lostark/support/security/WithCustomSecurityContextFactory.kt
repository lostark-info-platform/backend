package org.info.lostark.support.security

import org.info.lostark.fixture.createUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithCustomSecurityContextFactory : WithSecurityContextFactory<WithMockCustomUser> {
    override fun createSecurityContext(annotation: WithMockCustomUser): SecurityContext {
        return SecurityContextHolder.createEmptyContext().apply {
            val user = createUser(annotation.socialUid, annotation.socialProvider)
            authentication = UsernamePasswordAuthenticationToken(user, user.authorities)
        }
    }
}