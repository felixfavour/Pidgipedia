package com.felixfavour.pidgipedia.entity

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felixfavour.pidgipedia.util.HistorySize
import com.felixfavour.pidgipedia.util.Pidgipedia.HISTORY_LIMIT

@Dao
interface WordDao {
    /**
     * Method to get all words from words database*/
    @Transaction
    @Query("SELECT * FROM recentSearches ORDER BY wordRoomId DESC LIMIT :limit")
    fun getAllWordsSearch(limit: Int): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWordSearch(word: Word)

    @Query("SELECT * FROM recentSearches WHERE wordRoomId = :wordId")
    fun getWord(wordId: Long): Word

    @Query("DELETE FROM recentSearches")
    fun deleteRecentSearches()

    @Query("DELETE FROM recentSearches WHERE wordId = :wordId")
    fun deleteRecentSearchById(wordId: Long)

}