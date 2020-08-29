package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.LOADING
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.EVENTSTAMPS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HomeViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var localEventstamps: List<Eventstamp>

    private val _eventstamps = MutableLiveData<List<Eventstamp>>()
    val eventstamps: LiveData<List<Eventstamp>>
        get() = _eventstamps

    private val _unapprovedWords = MutableLiveData<List<Word>>()
    val unapprovedWords: LiveData<List<Word>>
        get() = _unapprovedWords

    private val _wordOfTheDay = MutableLiveData<Word>()
    val wordOfTheDay: LiveData<Word>
    get() = _wordOfTheDay.apply {
        loadWordOfTheDay()
    }

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private fun loadWordOfTheDay() {
        _wordOfTheDay.value = MockData.word
    }


    fun loadEventstamps() {
        _status.value = LOADING

        firebaseFirestore.collection(EVENTSTAMPS)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                _status.value = SUCCESS
                // All Eventstamps Objects from this specific RemoteUser
                val eventstamps = querySnapshot?.toObjects(Eventstamp::class.java)
                eventstamps?.sortByDescending { it.eventTime }
                _eventstamps.value = eventstamps
            }.addOnFailureListener {
                _status.value = FAILED
            }
    }

    fun loadUnapprovedWords() {
        val localUnapprovedWords = mutableListOf<Word>()
        firebaseFirestore.collection(SUGGESTED_WORDS)
            .orderBy("wordId", Query.Direction.DESCENDING)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                val words = querySnapshot.toObjects(Word::class.java)
                words.sortByDescending { it.dateCreated }
                words.forEach {
                    if (it.certified || it.rejected || it.approved) {
                    } else {
                        localUnapprovedWords.add(it)
                    }
                }
                _unapprovedWords.value =  localUnapprovedWords
            }
    }

}
