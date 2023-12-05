package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.info.lostark.common.domain.BaseEntity

@Entity
class RewardItem(
    @Column
    val name: String,

    @Column
    val icon: String,

    @Column
    val grade: String,

    @Column
    var startTimes: String? = null,

    id: Long = 0L
) : BaseEntity(id)
