package com.felixfavour.pidgipedia.entity

data class RankReward(
    val rankType: Int,
    val timeEarned: Long
): Eventstamp(rankRewardType = rankType, eventTime = timeEarned) {
}