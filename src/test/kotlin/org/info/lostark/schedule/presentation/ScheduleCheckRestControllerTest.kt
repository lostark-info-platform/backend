package org.info.lostark.schedule.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.schedule.command.application.ScheduleCheckService
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.bearer
import org.info.lostark.support.security.WithMockCustomUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.post

@WithMockCustomUser
@WebMvcTest(ScheduleCheckRestController::class)
class ScheduleCheckRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var scheduleCheckService: ScheduleCheckService

    @Test
    fun `스케줄을 체크하는 경우 200을 응답한다`() {
        every { scheduleCheckService.check(any(), any()) } just Runs

        mockMvc.post("/api/schedules/{scheduleId}/check", 1L) {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-check-post"))
        }
    }

    @Test
    fun `스케줄을 체크해제하는 경우 200을 응답한다`() {
        every { scheduleCheckService.uncheck(any(), any()) } just Runs

        mockMvc.post("/api/schedules/{scheduleId}/uncheck", 1L) {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-uncheck-post"))
        }
    }
}
