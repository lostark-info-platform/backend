package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.info.lostark.common.domain.BaseEntity
import java.time.LocalDateTime

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
    val startTime: LocalDateTime,

    @Column
    val endTime: LocalDateTime,

    @Column
    val image: String,
) : BaseEntity()