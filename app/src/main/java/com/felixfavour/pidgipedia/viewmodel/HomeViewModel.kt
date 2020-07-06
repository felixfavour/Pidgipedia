package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.util.Pidgipedia.EVENTSTAMPS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

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

    private fun loadWordOfTheDay() {
        _wordOfTheDay.value = MockData.word
    }


    fun loadEventstamps() {
        val eventstamps = mutableListOf<Eventstamp>()

        firebaseFirestore.collection(EVENTSTAMPS).get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                // All Eventstamps Objects from this specific RemoteUser

                querySnapshot?.documents?.forEach { eventstampField ->
                    val eventstamp = eventstampField.toObject(Eventstamp::class.java)
                    eventstamps.add(eventstamp!!)
                }
                _eventstamps.value = eventstamps
            }
    }

    fun loadUnapprovedWords() {
        val words = mutableListOf<Word>()
        firebaseFirestore.collection(SUGGESTED_WORDS)
            .whereEqualTo("approved", false)
            .whereEqualTo("rejected", false)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.documents.forEach { document ->
                    words.add(document.toObject(Word::class.java)!!)
                    _unapprovedWords.value = words
                }
            }
    }

}
