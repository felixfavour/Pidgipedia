package com.felixfavour.pidgipedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.entity.WordDatabase
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.felixfavour.pidgipedia.util.Rank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WordViewModel(application: Application): AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val database = WordDatabase.getInstance(application.applicationContext)!!
    private val wordDao = database.getWordDao()

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _bookmarked = MutableLiveData<Boolean>()
    val bookmarked: LiveData<Boolean>
        get() = _bookmarked

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user


    fun loadUser() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val remoteUser = documentSnapshot.toObject(RemoteUser::class.java)
                _user.value = remoteUser
            }
    }


    fun loadWord(wordId: String) {
        firebaseFirestore.collection(SUGGESTED_WORDS).document(wordId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val wordSnapshot = documentSnapshot.toObject(Word::class.java)
                _word.value = wordSnapshot
            }
    }


    fun toggleBookmarkWord() {
        /**
         * [wordLocal] is the value of word but the name is changed so as to bypass
         * similarity and conflict between variable names.*/
        val wordLocal = word.value
        if (wordLocal != null) {
            if (wordLocal.bookmarked) {
                firebaseFirestore.collection(SUGGESTED_WORDS).document(wordLocal.wordId)
                    .update("bookmarked", false)
                    .addOnSuccessListener {
                        _bookmarked.value = true
                        loadWord(wordLocal.wordId)
                    }
            } else {
                firebaseFirestore.collection(SUGGESTED_WORDS).document(wordLocal.wordId)
                    .update("bookmarked", true)
                    .addOnSuccessListener {
                        loadWord(wordLocal.wordId)
                    }
            }
        }
    }


    fun approveWord() {
        val userRank = user.value?.rank!!
        if (userRank <= Rank.RANK_CONTRIBUTOR) {
            firebaseFirestore.collection(SUGGESTED_WORDS).document(word.value?.wordId!!)
                .update(
                    mapOf(
                        "approved" to true,
                        "approvedAuthorId" to firebaseAuth.uid!!
                    )
                )
                .addOnSuccessListener {
                    _status.value = SUCCESS
                }
        } else {
            _status.value = FAILED
        }
    }


    fun disapproveWord() {
        val userRank = user.value?.rank!!
        if (userRank <= Rank.RANK_CONTRIBUTOR) {
            firebaseFirestore.collection(SUGGESTED_WORDS).document(word.value?.wordId!!)
                .update(
                    mapOf(
                        "rejected" to true,
                        "approvedAuthorId" to firebaseAuth.uid!!
                    )
                )
                .addOnSuccessListener {
                    _status.value = SUCCESS
                }
        } else {
            _status.value = FAILED
        }
    }

}
