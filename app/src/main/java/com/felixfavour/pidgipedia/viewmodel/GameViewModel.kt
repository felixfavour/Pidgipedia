package com.felixfavour.pidgipedia.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Quiz
import com.felixfavour.pidgipedia.util.MockData
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlin.random.Random

class GameViewModel: ViewModel() {

    companion object {
        const val TOTAL_QUESTIONS = 10
    }

    /** [score] records increase in */
    private var score = 0


    private val _quiz = MutableLiveData<Quiz?>(null)
    val quiz: LiveData<Quiz?>
        get() = _quiz

    private val _currentQuestion = MutableLiveData<Int>(0)
    val currentQuestion: LiveData<Int>
        get() = _currentQuestion

    private val _totalQuestions = MutableLiveData<Int>(TOTAL_QUESTIONS)
    val totalQuestions: LiveData<Int>
        get() = _totalQuestions

    private val _currentScore = MutableLiveData<Int>(0)
    val currentScore: LiveData<Int>
        get() = _currentScore.apply {
            _currentScore.value = score
        }

    private val _highScore = MutableLiveData<Long>()
    val highScore: LiveData<Long>
        get() = _highScore.apply {
            _highScore.value = 5
        }

    private val _timePerQuestion = MutableLiveData<Int>(0)
    val timePerQuestion: LiveData<Int>
        get() = _timePerQuestion

    private val _countdownTimer = MutableLiveData<Int>(0)
    val countdownTimer: LiveData<Int>
        get() = _countdownTimer


    private fun loadHighScore() {
        _highScore.value = MockData.user2.highestScore
    }


    fun loadQuiz() {
        _quiz.value = MockData.quizzes[_currentQuestion.value!!]
    }


    fun isAnswerCorrect(checkedAnswerId: Int) {
        val quizObj = quiz.value!!
        var checkedAnswer = 0

        when(checkedAnswerId) {
            R.id.answer1 -> checkedAnswer = 0
            R.id.answer2 -> checkedAnswer = 1
            R.id.answer3 -> checkedAnswer = 2
            R.id.answer4 -> checkedAnswer = 3
        }

        if (checkedAnswer == quizObj.correctAnswerIndex) {
            score += 1
        }
        _currentQuestion.value = _currentQuestion.value!! + 1
    }


    /**
     * Method to initialize all necessary values to zero*/
    fun restartGame() {
        score = 0
        _currentQuestion.value = 0
    }

    fun updateTimer(seconds: Long) {
        _countdownTimer.value = seconds.div(1000).toInt()
    }

}
