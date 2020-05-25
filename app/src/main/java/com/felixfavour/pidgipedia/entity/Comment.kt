package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import com.felixfavour.pidgipedia.entity.CommentResponse.CommentResponse
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Comment (
    val commentContent: String,
    val author: @RawValue User,
    val dateCreated: Long,
    val commentResponses: @RawValue ArrayList<CommentResponse>) : Parcelable {
}