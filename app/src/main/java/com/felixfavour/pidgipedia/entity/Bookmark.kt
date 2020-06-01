package com.felixfavour.pidgipedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookmark_id")
    val bookmarkId: Int,
    @ColumnInfo(name = "bookmark_word_id")
    val word_id: Int,
    @ColumnInfo(name = "date_created")
    val dateCreated: Long
)