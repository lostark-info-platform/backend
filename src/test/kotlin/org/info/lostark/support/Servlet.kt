package org.info.lostark.support

import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockHttpServletRequestDsl


fun MockHttpServletRequestDsl.bearer(token: String) {
    header(HttpHeaders.AUTHORIZATION, bearerToken(token))
}

private fun bearerToken(token: String): String = "Bearer $token"