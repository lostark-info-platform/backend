package org.info.lostark.common.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.common.application.StaticConfigService
import org.info.lostark.common.presentation.dto.ClientBaseUrlRequest
import org.info.lostark.support.RestControllerTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.post

@WebMvcTest(StaticConfigController::class)
class StaticConfigControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var staticConfigService: StaticConfigService

    @Test
    fun `client base url을 변경한다`() {
        every { staticConfigService.changeClientBaseUrl(any()) } just Runs

        mockMvc.post("/config/clientBaseUrl") {
            jsonContent(ClientBaseUrlRequest("localhost:3535"))
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("change-client-base-url-post"))
        }
    }
}