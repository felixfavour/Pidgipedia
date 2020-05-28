package com.felixfavour.pidgipedia.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankReward(
    val rankType: Int,
    val timeEarned: Long
): Eventstamp(rankRewardType = rankType, eventTime = timeEarned, comments = arrayListOf(),
    word = null, humanEntity = null) {

}