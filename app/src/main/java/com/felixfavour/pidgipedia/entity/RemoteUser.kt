package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import com.felixfavour.pidgipedia.util.Rank
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RemoteUser(
    val userId: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dateOfBirth: Long = 0L,
    val rank: Long = Rank.RANK_JJC,
    val location: String? = "",
    val bio: String = "***empty Bio***",
    val badges: List<String> = emptyList(),
    val suggestedWords: List<String> = emptyList(),
    val approvedWords: List<String> = emptyList(),
    val highestScore: Long = 0L,
    val profileImageURL: String = "",
    val username: String = "",
    val bookmarks: List<String> = emptyList()
): Parcelable {

    override fun equals(other: Any?): Boolean {
        other as RemoteUser
        return this.userId == other.userId
    }

    override fun toString(): String {
        return "$firstName $lastName".trim()
    }

}