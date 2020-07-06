package com.felixfavour.pidgipedia.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userID: Int = 0,
    @ColumnInfo(name = "firstName")
    val firstName: String = "",
    @ColumnInfo(name = "lastName")
    val lastName: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: Long = 0L,
    @ColumnInfo(name = "rank")
    val rank: Int = 0,
    @ColumnInfo(name = "location")
    val location: String? = "",
    @ColumnInfo(name = "bio")
    val bio: String = "***empty Bio***",
    val badges: List<String> = emptyList(),
    val suggestedWords: List<String> = emptyList(),
    val approvedWords: List<String> = emptyList(),
    @ColumnInfo(name = "highest_score")
    val highestScore: Int = 0,
    val profileImageURL: @RawValue String? = "",
    val username: String = ""
): Parcelable {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}