package org.info.lostark.officialschedule.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.info.lostark.officialschedule.query.dto.OfficialContentResponse
import org.info.lostark.officialschedule.query.dto.OfficialContentSummaryResponse
import org.info.lostark.officialschedule.query.dto.OfficialContentsDetailsQueryResponse
import org.info.lostark.officialschedule.query.dto.RewardItemResponse
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

    @Test
    fun `필드보스 컨텐츠의 지역을 조회한다`() {
        val response = listOf(
            "갈라진 땅",
            "격류의 섬",
            "대우림",
            "레갸르방크 대평원",
            "레갸르방크 대평원",
            "머무른 시간의 호수",
            "무쇠망치 작업장",
            "베르닐 삼림",
            "붉은 달의 흔적",
            "소리의 숲",
            "알트아이젠",
            "얼음과 불의 섬",
            "오르비스섬",
            "칸다리아 영지",
            "티카티카 군락지",
            "파괴된 제나일",
            "필레니소스 산"
        )
        every { officialScheduleQueryService.getFieldBossLocation() } returns response

        mockMvc.get("/api/official/field-boss/location")
            .andExpect {
                status { isOk() }
                content { success(response) }
            }.andDo {
                handle(document("official-field-boss-location-get"))
            }
    }

    @Test
    fun `공식스케줄을 컨텐츠별로 조회한다`() {
        val response = createOfficialContentsDetailsQueryResponse()

        every { officialScheduleQueryService.getContents(any(), any()) } returns response

        mockMvc.get("/api/official/contents?category=${Contents.ADVENTURE_ISLAND.name}&location=")
            .andExpect {
                status { isOk() }
                content { success(response) }
            }.andDo {
                handle(document("official-contents-get"))
            }
    }

    private fun createOfficialContentsDetailsQueryResponse() =
        OfficialContentsDetailsQueryResponse(
            LocalDateTime.now(), listOf(
                OfficialContentResponse(
                    "하모니 섬",
                    "https://cdn-lostark.game.onstove.com/efui_iconatlas/island_icon/island_icon_55.png",
                    250,
                    "하모니 섬",
                    null,
                    null,
                    null,
                    null,
                    listOf(
                        RewardItemResponse(
                            "하모니 섬의 마음",
                            "https://cdn-lostark.game.onstove.com/efui_iconatlas/tokenitem/tokenitem_9.png",
                            "유물",
                            null
                        ),
                        RewardItemResponse(
                            "천상의 하모니",
                            "https://cdn-lostark.game.onstove.com/efui_iconatlas/use/use_4_106.png",
                            "일반",
                            null
                        )
                    )
                )
            )
        )

    private fun createOfficialContents(): List<OfficialContentSummaryResponse> {
        return listOf(
            OfficialContentSummaryResponse(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_14_142.png",
                "필드보스",
                LocalDateTime.now().plusHours(3)
            ),
            OfficialContentSummaryResponse(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_13_11.png",
                "카오스게이트",
                LocalDateTime.now().plusHours(1)
            ),
            OfficialContentSummaryResponse("", "모험 섬", LocalDateTime.now().plusMinutes(3)),
            OfficialContentSummaryResponse(
                "https://cdn-lostark.game.onstove.com/efui_iconatlas/island_icon/island_icon_131.png",
                "태초의 섬",
                LocalDateTime.now().plusMinutes(10)
            ),
            OfficialContentSummaryResponse("", "도전 어비스 던전", null),
            OfficialContentSummaryResponse("", "도전 가디언 토벌", LocalDateTime.now().plusHours(3))
        )
    }
}