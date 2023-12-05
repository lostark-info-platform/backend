package org.info.lostark.officialschedule.command.dommain

import jakarta.persistence.*
import org.info.lostark.common.domain.BaseEntity

@Entity
@Table(name = "contents_calendar")
class ContentsCalendar(
    @Column
    val categoryName: String,

    @Column
    val contentsName: String,

    @Column
    val contentsIcon: String,

    @Column
    val minItemLevel: Int,

    @Lob
    @Column(length = 100000)
    val startTimes: String,

    @Column
    val location: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val rewardItems: MutableList<RewardItem>,

    id: Long = 0L
) : BaseEntity(id)