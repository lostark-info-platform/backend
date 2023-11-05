package org.info.lostark.user.presentation

import org.info.lostark.fixture.createUser
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.bearer
import org.info.lostark.user.query.dto.UserQueryResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.get

@WebMvcTest(UserInfoRestController::class)
class UserInfoRestControllerTest : RestControllerTest() {
    @Test
    fun `회원이 자신의 정보를 조회한다`() {
        val response = UserQueryResponse(createUser())

        mockMvc.get("/api/users/me") {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(MockMvcRestDocumentation.document("user-me-get"))
        }
    }

    @Test
    fun `유효하지 않은 토큰으로 개인정보 요청 시 401 Unauthorized 반환한다`() {
        mockMvc.get("/api/users/me") {
            header(HttpHeaders.AUTHORIZATION, "invalid_token")
        }.andExpect {
            status { isUnauthorized() }
            content { error("로그인 정보가 정확하지 않습니다.") }
        }.andDo {
            handle(MockMvcRestDocumentation.document("user-me-get-unauthorized"))
        }
    }
}
