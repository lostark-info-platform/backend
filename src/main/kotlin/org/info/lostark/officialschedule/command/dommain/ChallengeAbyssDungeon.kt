package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.info.lostark.common.domain.BaseEntity

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
    val startTime: String,

    @Column
    val endTime: String,

    @Column
    val image: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val rewardItems: MutableList<RewardItem>,

    id: Long = 0L
) : BaseEntity(id)

