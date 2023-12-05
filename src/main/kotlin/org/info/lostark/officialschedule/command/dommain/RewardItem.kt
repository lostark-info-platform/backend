package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.info.lostark.common.domain.BaseEntity

@Entity
@Table(name = "reward_item")
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
