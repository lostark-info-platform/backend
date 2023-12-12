package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.info.lostark.common.domain.BaseEntity

@Entity
@Table(name = "guardian_raid")
class GuardianRaid(
    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val minCharacterLevel: Int,

    @Column
    val minItemLevel: Int,

    @Column
    val startTime: String,

    @Column
    val endTime: String,

    @Column
    val image: String,
) : BaseEntity()