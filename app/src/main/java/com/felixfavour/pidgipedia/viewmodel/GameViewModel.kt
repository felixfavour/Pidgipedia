package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Quiz
import com.felixfavour.pidgipedia.util.Pidgipedia.QUIZZES
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GameViewModel: ViewModel() {

    companion object {
        const val TOTAL_QUESTIONS = 10
    }

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status


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
        get() = _highScore

    private val _isHighScore = MutableLiveData<Boolean>()
    val isHighScore: LiveData<Boolean>
        get() = _isHighScore

    private val _countdownTimer = MutableLiveData<Int>(0)
    val countdownTimer: LiveData<Int>
        get() = _countdownTimer


    fun loadQuiz() {
        firebaseFirestore.collection(QUIZZES)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                val quizzes = querySnapshot.toObjects(Quiz::class.java)
                if (quizzes.isNotEmpty()) {
                    _quiz.value = quizzes[0]
                }
                _status
            }
    }


    fun isAnswerCorrect(checkedAnswerId: Int) {
        val quizObj = quiz.value!!
        var checkedAnswer = 0L

        when(checkedAnswerId) {
            R.id.answer1 -> checkedAnswer = 0L
            R.id.answer2 -> checkedAnswer = 1L
            R.id.answer3 -> checkedAnswer = 2L
            R.id.answer4 -> checkedAnswer = 3L
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

    fun setHighScore(highScoreParam: Long) {
        var highScoreLocal = 0L
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE).addOnSuccessListener { documentSnapshot ->
                _highScore.value = documentSnapshot["highestScore"] as Long
                highScoreLocal = documentSnapshot["highestScore"] as Long

                if (highScoreParam > highScoreLocal) {
                    _isHighScore.value = true
                    firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
                        .update("highestScore", highScoreParam)
                } else {
                    _isHighScore.value = false
                }
            }

    }

}
