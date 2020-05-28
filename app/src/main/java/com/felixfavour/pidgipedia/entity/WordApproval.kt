package com.felixfavour.pidgipedia.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordApproval(
    val wordApproved: Word,
    val timeApproved: Long,
    val approvedBy: User,
    val approvalComments: ArrayList<Comment>
) : Eventstamp(
    word = wordApproved,
    eventTime = timeApproved,
    humanEntity = approvedBy,
    isApproved = true,
    comments = approvalComments
) {
}