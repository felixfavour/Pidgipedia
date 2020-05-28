package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Eventstamp(
    val word: Word?,
    val comments: ArrayList<Comment>,
    val badgeRewardType: Int? = null,
    val rankRewardType: Int? = null,
    val isApproved: Boolean = false,
    val isSuggested: Boolean = false,
    val isWordComment: Boolean = false,
    val isCommentResponse: Boolean = false,
    val eventTime: Long,
    val humanEntity: User?
): Parcelable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

}