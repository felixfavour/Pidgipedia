package com.felixfavour.pidgipedia.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WordDao {
    /**
     * Method to get all words from words database*/
    @Transaction
    @Query("SELECT * FROM words")
    fun getAllWords(): LiveData<List<Word>>

    @Insert()
    fun insertWord(word: Word)

    @Query("SELECT * FROM words WHERE wordRoomId = :wordId")
    fun getWord(wordId: Long): LiveData<Word>

    @Query("SELECT * FROM words where bookmarked = 1")
    fun getBookmarkedWords(): LiveData<List<Word>>

}