package org.info.lostark.officialschedule.command.dommain

import org.springframework.data.jpa.repository.JpaRepository

interface ContentsCalendarRepository : JpaRepository<ContentsCalendar, Long>
