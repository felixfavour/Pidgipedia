package com.felixfavour.pidgipedia.entity

import androidx.room.*

@Dao
interface BookmarkDao {

    @Transaction
    @Query("SELECT * from bookmarks ORDER BY date_created")
    fun getAllBookmarks(): List<Bookmark>

    @Delete
    fun deleteBookmark(word: Word)

    @Insert
    fun addBookmark(word: Word)
}