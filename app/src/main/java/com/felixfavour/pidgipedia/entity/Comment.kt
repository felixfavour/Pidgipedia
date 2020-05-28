package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Comment (
    val commentContent: String,
    val author: User,
    val dateCreated: Long) : Parcelable {
}

