package com.felixfavour.pidgipedia.entity

import android.graphics.Bitmap
import android.os.Parcelable
import android.provider.MediaStore
import androidx.room.*
import com.felixfavour.pidgipedia.util.ListConverter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    val wordRoomId: Int = 0,
    var wordId: String = "",
    @ColumnInfo(name = "word_name")
    val name: String = "",
    @ColumnInfo(name = "word_meaning")
    val meaning: String = "",
    @ColumnInfo(name = "etymology")
    val etymology: String = "",
    @ColumnInfo(name = "plural")
    val plural: String? = "",
    @ColumnInfo(name = "part_of_speech")
    val partOfSpeech: String = "",
    @ColumnInfo(name = "syllables")
    val syllables: Long = 0,
    @ColumnInfo(name = "syllabic_division")
    val syllabicDivision: String = "",
    @ColumnInfo(name = "english_equivalent")
    val englishEquivalent: String = "",
    @ColumnInfo(name = "image_reference")
    val imageReference: String? = null,
    @ColumnInfo(name = "transcription")
    val transcription: String? = null,
    @ColumnInfo(name = "audio_reference")
    val pronunciationReference: String? = null,
    @ColumnInfo(name = "synonyms")
    val synonyms: List<String> = emptyList(),
    @ColumnInfo(name = "sentences")
    val sentences: List<String> = emptyList(),
    @ColumnInfo(name = "word_author_id")
    val authorId: String = "",
    @ColumnInfo(name = "date_created")
    val dateCreated: Long = 0L,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = 0L,
    @ColumnInfo(name = "is_approved")
    val approved: Boolean = false,
    val rejected: Boolean = false,
    val approvedAuthorId:String = "",
    @ColumnInfo(name = "word_of_the_day")
    val wordOfTheDay_date: Long = 0L,
    val comments: List<String> = emptyList()
) : Parcelable {
    constructor(word: Word, wordId: String) : this()
}
