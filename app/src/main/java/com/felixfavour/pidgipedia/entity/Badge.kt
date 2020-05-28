package com.felixfavour.pidgipedia.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class BadgeReward (
    val badgeType: Int,
    val timeEarned: Long
) : Eventstamp(badgeRewardType = badgeType, eventTime = timeEarned, comments = arrayListOf(),
    word = null, humanEntity = null) {

}