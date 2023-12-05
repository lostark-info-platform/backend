package org.info.lostark.user.presentation

import org.info.lostark.fixture.createUser
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.bearer
import org.info.lostark.support.security.WithMockCustomUser
import org.info.lostark.user.query.dto.UserQueryResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.get

@WithMockCustomUser
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
}
