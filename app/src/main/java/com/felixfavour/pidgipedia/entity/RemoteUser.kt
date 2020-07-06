package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RemoteUser(
    val userId: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dateOfBirth: Long = 0L,
    val rank: Long = 0L,
    val location: String? = "",
    val bio: String = "***empty Bio***",
    val badges: List<String> = emptyList(),
    val suggestedWords: List<String> = emptyList(),
    val approvedWords: List<String> = emptyList(),
    val highestScore: Long = 0L,
    val profileImageURL: String = "",
    val username: String = ""
): Parcelable {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}