package com.felixfavour.pidgipedia.entity

open class Eventstamp(
    val word: Word? = null,
    val comment: Comment? = null,
    val badgeRewardType: Int? = null,
    val rankRewardType: Int? = null,
    val isApproved: Boolean = false,
    val isSuggested: Boolean = false,
    val eventTime: Long,
    val humanEntity: User? = null
) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

}