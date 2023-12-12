package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.*
import org.info.lostark.common.domain.BaseEntity

@Entity
@Table(name = "guardian_raid_reward_item")
class GuardianRaidRewardItem(
    @Column
    val expeditionItemLevel: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    val rewardItems: MutableList<RewardItem>
) : BaseEntity()