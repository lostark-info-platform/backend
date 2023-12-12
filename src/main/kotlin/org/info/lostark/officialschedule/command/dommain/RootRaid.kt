package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import org.info.lostark.common.domain.BaseEntity

@Entity
class RootRaid(
    @OneToMany(cascade = [CascadeType.ALL])
    val raids: MutableList<GuardianRaid>,

    @OneToMany(cascade = [CascadeType.ALL])
    val items: MutableList<GuardianRaidRewardItem>
) : BaseEntity()