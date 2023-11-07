package org.info.lostark.schedule.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.fixture.createScheduleQueryResponse
import org.info.lostark.fixture.createScheduleRegisterRequest
import org.info.lostark.fixture.createScheduleUpdateRequest
import org.info.lostark.schedule.command.application.ScheduleCheckService
import org.info.lostark.schedule.command.application.ScheduleService
import org.info.lostark.schedule.query.ScheduleQueryService
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.bearer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(ScheduleRestController::class)
class ScheduleRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var scheduleQueryService: ScheduleQueryService

    @MockkBean
    private lateinit var scheduleService: ScheduleService

    @MockkBean
    private lateinit var scheduleCheckService: ScheduleCheckService

    @Test
    fun `자신의 모든 스케즐을 조회한다`() {
        val response = listOf(
            createScheduleQueryResponse(scheduleId = 1L),
            createScheduleQueryResponse(scheduleId = 2L),
            createScheduleQueryResponse(scheduleId = 3L)
        )
        every { scheduleQueryService.findAllScheduleByUserId(any()) } returns response

        mockMvc.get("/api/schedules") {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-me-list-get"))
        }
    }

    @Test
    fun `스케줄을 정상 등록하는 경우 200을 응답한다`() {
        val request = createScheduleRegisterRequest()
        every { scheduleService.register(any()) } just Runs

        mockMvc.post("/api/schedules") {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-post"))
        }
    }

    @Test
    fun `스케줄을 정상 수정하는 경우 200을 응답한다`() {
        val request = createScheduleUpdateRequest()
        every { scheduleService.update(any()) } just Runs

        mockMvc.post("/api/schedules/{scheduleId}", 1L) {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-update-post"))
        }
    }

    @Test
    fun `스케줄을 정상 삭제하는 경우 200을 응답한다`() {
        every { scheduleService.delete(any(), any()) } just Runs

        mockMvc.delete("/api/schedules/{scheduleId}", 1L) {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(MockMvcRestDocumentation.document("schedule-delete"))
        }
    }
}
