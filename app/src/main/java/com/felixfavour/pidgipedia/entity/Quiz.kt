package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import androidx.room.*
import com.felixfavour.pidgipedia.util.ListConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    val question_id: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answers")
    @TypeConverters(ListConverter::class)
    val answers: List<String>,
    @ColumnInfo(name = "correct_answer_index")
    val correctAnswerIndex: Int,
    @ColumnInfo(name = "date_created")
    val dateCreated: Long,
    @ColumnInfo(name = "author_id")
    val authorId: Int,
    @ColumnInfo(name = "is_approved")
    val isApproved: Boolean
) : Parcelable {
}