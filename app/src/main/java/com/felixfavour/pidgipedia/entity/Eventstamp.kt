package com.felixfavour.pidgipedia.entity

open class Eventstamp(
    val word: Word? = null,
    val comment: Comment? = null,
    val badgeRewardType: Int? = null,
    val rankRewardType: Int? = null,
    val eventTime: Long,
    val humanEntity: User? = null
) {

}