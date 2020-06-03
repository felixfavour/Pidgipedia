package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val _highScore = MutableLiveData<Int>()
    val highScore: LiveData<Int>
        get() = _highScore.apply {
            loadHighScore()
        }

    private val _rank = MutableLiveData<Int>()
    val rank: LiveData<Int>
        get() = _rank.apply {
            loadUserRank()
        }

    private fun loadUserRank() {
        _rank.value = 2
    }

    private fun loadHighScore() {
        _highScore.value = 10
    }
}