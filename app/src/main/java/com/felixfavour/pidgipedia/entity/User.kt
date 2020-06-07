package com.felixfavour.pidgipedia.entity

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userID: Int,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: Long,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "bio")
    val bio: String,
    val badges: List<String>,
    val postedWords: List<String>,
    val approvedWords: List<String>,
    @ColumnInfo(name = "highest_score")
    val highestScore: Int,
    val profileImageUrl: @RawValue String? = null
): Parcelable {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}