package org.info.lostark.officialschedule.query.dao.support

import org.info.lostark.officialschedule.command.dommain.RootRaid
import org.springframework.data.jpa.repository.JpaRepository

interface RootRaidDaoSupport : JpaRepository<RootRaid, Long>