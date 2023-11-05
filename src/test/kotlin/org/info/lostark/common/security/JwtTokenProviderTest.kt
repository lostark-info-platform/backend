package org.info.lostark.common.security

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

const val PAYLOAD = "user@email.com"
const val INVALID_TIME = -10L
const val INVALID_TOKEN = ""

class JwtTokenProviderTest : StringSpec({
    "payload를 넣어 토큰을 생성하고 토큰에서 다시 payload를 불러올 수 있는지 확인한다" {
        val jwtTokenProvider = JwtTokenProvider()
        val token = jwtTokenProvider.createToken(PAYLOAD)
        jwtTokenProvider.getSubject(token) shouldBe PAYLOAD
    }

    "유효기간이 지난 토큰은 검증에 실패한다" {
        val jwtTokenProvider = JwtTokenProvider(expirationInMilliseconds = INVALID_TIME)
        val token = jwtTokenProvider.createToken(PAYLOAD)
        jwtTokenProvider.isValidToken(token) shouldBe false
    }

    "올바르지 않은 토큰은 검증에 실패한다" {
        val jwtTokenProvider = JwtTokenProvider()
        jwtTokenProvider.isValidToken(INVALID_TOKEN) shouldBe false
    }
})
