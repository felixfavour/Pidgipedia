package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import androidx.room.*
import com.felixfavour.pidgipedia.util.ListConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Quiz(
    val question_id: Long,
    val question: String,
    val answers: List<String>,
    val difficulty: Long,
    val correctAnswerIndex: Long,
    val dateCreated: Long,
    val authorId: String,
    val isApproved: Boolean
) : Parcelable {
}