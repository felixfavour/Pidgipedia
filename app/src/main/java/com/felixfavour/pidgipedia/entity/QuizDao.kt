package com.felixfavour.pidgipedia.entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface QuizDao {
    @Transaction
    @Query("SELECT * FROM quizzes LIMIT 10")
    fun getQuizQuestions(): List<Quiz>
}