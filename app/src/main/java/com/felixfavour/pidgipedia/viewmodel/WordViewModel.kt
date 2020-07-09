package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WordViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

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


    fun approveWord() {
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
    }


    fun disapproveWord() {
        firebaseFirestore.collection(SUGGESTED_WORDS).document(word.value?.wordId!!)
            .update(
                mapOf(
                    "rejected" to true,
                    "approvedAuthorId" to firebaseAuth.uid!!
                ))
            .addOnSuccessListener {
                _status.value = SUCCESS
            }
    }

}
