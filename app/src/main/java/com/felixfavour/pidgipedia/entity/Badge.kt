package com.felixfavour.pidgipedia.entity

data class BadgeReward (
    val badgeType: Int,
    val timeEarned: Long
) : Eventstamp(badgeRewardType = badgeType, eventTime = timeEarned) {

}