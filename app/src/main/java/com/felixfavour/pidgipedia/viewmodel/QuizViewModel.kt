package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuizViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _highScore = MutableLiveData<Int>()
    val highScore: LiveData<Int>
        get() = _highScore.apply {
            loadHighScore()
        }

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _rank = MutableLiveData<Long>()
    val rank: LiveData<Long>
        get() = _rank.apply {
            loadUserRank()
        }

    private fun loadUserRank() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val rank = documentSnapshot["rank"] as Long?
                _rank.value = rank
            }
    }

    private fun loadHighScore() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val highestScore = documentSnapshot["highestScore"] as Long?
                _highScore.value = highestScore?.toInt()
            }
    }
}