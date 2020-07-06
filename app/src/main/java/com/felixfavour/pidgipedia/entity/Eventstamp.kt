package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Eventstamp(
    val eventstampId: Long = 0,
    val wordId: String? = "",
    val badgeRewardType: String? = null,
    val rankRewardType: Long? = null,
    val approved: Boolean = false,
    val suggested: Boolean = false,
    val wordComment: Boolean = false,
    val commentResponse: Boolean = false,
    val eventTime: Long? = null,
    val humanEntityId: String? = null
): Parcelable {
    override fun equals(other: Any?): Boolean {
        other as Eventstamp
        return this.eventstampId == other.eventstampId
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

}