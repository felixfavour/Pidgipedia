package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment (
    val commentId: String = "",
    val commentContent: String = "",
    val authorId: String = "",
    val wordId: String = "",
    val respondingTo: String = "",
    val dateCreated: Long = 0L,
    val unlisted: Boolean = false
) : Parcelable {

}

