package com.felixfavour.pidgipedia.entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WordDao {
    /**
     * Method to get all words from words database*/
    @Transaction
    @Query("SELECT * FROM users")
    fun getAllWords(): List<UserAndWord>

    /**
     * Method to get all words with initial specified from words database*/
}