package org.info.lostark.officialschedule.query.dao.support

import org.info.lostark.officialschedule.command.dommain.ContentsCalendar
import org.springframework.data.jpa.repository.JpaRepository

interface ContentsCalendarDaoSupport : JpaRepository<ContentsCalendar, Long> {
    fun findAllByCategoryNameIn(categories: List<String>): List<ContentsCalendar>
}