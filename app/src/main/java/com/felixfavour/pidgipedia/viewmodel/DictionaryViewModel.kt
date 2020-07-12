package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DictionaryViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _recentSearches = MutableLiveData<List<Word>>()
    val recentSearches: LiveData<List<Word>>
        get() = _recentSearches.apply {
            loadRecentSearches()
        }

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words.apply {
            if (this.value == null) loadWords()
        }

    private fun loadRecentSearches() {
        _recentSearches.value = listOf()
    }

    private fun loadWords() {
        firebaseFirestore.collection(Pidgipedia.SUGGESTED_WORDS)
            .orderBy("name")
            .get(Pidgipedia.SOURCE)
            .addOnSuccessListener { querySnapshot ->
                _words.value = querySnapshot.toObjects(Word::class.java)
            }
    }

}