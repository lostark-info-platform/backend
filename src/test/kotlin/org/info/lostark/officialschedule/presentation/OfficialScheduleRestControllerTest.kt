package org.info.lostark.officialschedule.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.info.lostark.officialschedule.query.OfficialContent
import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.info.lostark.support.RestControllerTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@WebMvcTest(OfficialScheduleRestController::class)
class OfficialScheduleRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var officialScheduleQueryService: OfficialScheduleQueryService

    @Test
    fun `홈 화면 콘텐츠 내용을 조회한다`() {
        val response = createOfficialContents()
        every { officialScheduleQueryService.getSummary() } returns response

        mockMvc.get("/api/official/summary")
            .andExpect {
                status { isOk() }
                content { success(response) }
            }.andDo {
                handle(document("official-summary-get"))
            }
    }

    private fun createOfficialContents(): List<OfficialContent> {
        return listOf(
            OfficialContent(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_14_142.png",
                "필드보스",
                LocalDateTime.now().plusHours(3)
            ),
            OfficialContent(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_13_11.png",
                "카오스게이트",
                LocalDateTime.now().plusHours(1)
            ),
            OfficialContent("", "모험 섬", LocalDateTime.now().plusMinutes(3)),
            OfficialContent(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/island_icon/island_icon_131.png",
                "태초의 섬",
                LocalDateTime.now().plusMinutes(10)
            ),
            OfficialContent("", "도전 어비스 던전", null),
            OfficialContent("", "도전 가디언 토벌", LocalDateTime.now().plusHours(3))
        )
    }
}