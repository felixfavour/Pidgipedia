package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dateOfBirth: Long = 0L,
    val rank: Int = 0,
    val location: String? = "",
    val bio: String = "***empty Bio***",
    val badges: List<String> = emptyList(),
    val suggestedWords: List<String> = emptyList(),
    val approvedWords: List<String> = emptyList(),
    val highestScore: Int = 0,
    val profileImageURL: @RawValue String? = "",
    val username: String = ""
): Parcelable {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}