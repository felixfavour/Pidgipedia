package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: Long,
    val rank: Int,
    val location: String,
    val bio: String,
    val postedWords: ArrayList<Word>,
    val approvedWords: ArrayList<Word>
): Parcelable {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}