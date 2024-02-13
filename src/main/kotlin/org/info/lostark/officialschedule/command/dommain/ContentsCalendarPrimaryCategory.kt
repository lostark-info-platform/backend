package org.info.lostark.officialschedule.command.dommain

enum class ContentsCalendarPrimaryCategory(override val value: String, override val iconUrl: String) :
    ContentsCategory {
    FIELD_BOSS("필드보스", "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_14_142.png"),
    CHAOS_GATE("카오스게이트", "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_13_11.png"),
    ADVENTURE_ISLAND("모험 섬", ""),
    BEGINNING_ISLAND("태초의 섬", "https://cdn-lostark.game.onstove.com/efui_iconatlas/island_icon/island_icon_131.png");

    companion object {
        val CONTENTS_CALENDAR_PRIMARY_CATEGORIES = ContentsCalendarPrimaryCategory.values().map { it.value }
    }
}