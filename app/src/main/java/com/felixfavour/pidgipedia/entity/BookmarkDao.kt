package com.felixfavour.pidgipedia.entity

import androidx.room.*

@Dao
interface BookmarkDao {

    @Transaction
    @Query("SELECT * from words ORDER BY date_created")
    fun getAllBookmarks(): List<WordAndBookmark>

    @Delete
    fun deleteBookmark(word: Word)

    @Insert
    fun addBookmark(word: Word)
}