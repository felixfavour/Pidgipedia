package com.felixfavour.pidgipedia.entity

import android.graphics.Bitmap
import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Word(
    val name: String,
    val meaning: String,
    val etymology: String,
    val plural: String?,
    val partOfSpeech: String,
    val syllables: Int,
    val syllabicDivision: String,
    val englishEquivalent: String,
    val image: Bitmap?,
    val transcription: String,
    val pronunciation: @RawValue MediaStore.Audio?,
    val synonyms: ArrayList<String>,
    val sentences: ArrayList<String>,
    val comments: @RawValue ArrayList<Comment>,
    val author: @RawValue User,
    val authorsId: @RawValue ArrayList<User>,
    val dateCreated: Long,
    val lastUpdated: Long,
    val isApproved: Boolean,
    val wordOfTheDay_date: Long
) : Parcelable {
}