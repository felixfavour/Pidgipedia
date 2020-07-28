package com.felixfavour.pidgipedia.entity

import android.graphics.Bitmap
import android.os.Parcelable
import android.provider.MediaStore
import androidx.room.*
import com.felixfavour.pidgipedia.util.ListConverter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "recentSearches")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val wordRoomId: Int = 0,
    var wordId: String = "",
    val name: String = "",
    val meaning: String = "",
    val etymology: String = "",
    val plural: String? = "",
    val partOfSpeech: String = "",
    val syllables: Long = 0,
    val syllabicDivision: String = "",
    val englishEquivalent: String = "",
    val imageReference: String? = null,
    val transcription: String? = null,
    val pronunciationReference: String? = null,
    val synonyms: List<String> = emptyList(),
    val sentences: List<String> = emptyList(),
    val authorId: String = "",
    val dateCreated: Long = 0L,
    val lastUpdated: Long = 0L,
    val approved: Boolean = false,
    val rejected: Boolean = false,
    val derogatory: Boolean = false,
    val certified: Boolean = false,
    val approvedAuthorId:String = "",
    val wordOfTheDay_date: Long = 0L,
    val comments: List<String> = emptyList()
) : Parcelable {

    override fun toString(): String {
        return this.name
    }
}
