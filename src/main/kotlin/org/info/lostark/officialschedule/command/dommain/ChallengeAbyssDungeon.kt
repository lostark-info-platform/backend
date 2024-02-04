package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.*
import org.info.lostark.common.domain.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "challenge_abyss_dungeon")
class ChallengeAbyssDungeon(
    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val minCharacterLevel: Int,

    @Column
    val minItemLevel: Int,

    @Column
    val areaName: String,

    @Column
    val startTime: LocalDateTime,

    @Column
    val endTime: LocalDateTime,

    @Column
    val image: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val rewardItems: MutableList<RewardItem>,

    id: Long = 0L
) : BaseEntity(id)

