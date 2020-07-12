package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import androidx.room.*
import com.felixfavour.pidgipedia.util.ListConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Quiz(
    val questionId: String = "",
    val question: String = "",
    val answers: List<String> = emptyList(),
    val difficulty: Long = 0,
    val correctAnswerIndex: Long = 0,
    val dateCreated: Long = 0,
    val authorId: String = "",
    val isApproved: Boolean = false
) : Parcelable {
}