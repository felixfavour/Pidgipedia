package com.felixfavour.pidgipedia.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.entity.WordDatabase
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.util.Pidgipedia.HISTORY_LIMIT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DictionaryViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val wordDatabase = WordDatabase.getInstance(application.applicationContext)?.getWordDao()

    private val _recentSearches = MutableLiveData<List<Word>>()
    val recentSearches: LiveData<List<Word>>
        get() = _recentSearches

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words.apply {
            if (this.value == null) loadWords()
        }

    fun loadRecentSearches() {
        viewModelScope.launch {
            _recentSearches.value = getSearches()
        }
    }

    private fun loadWords() {
        firebaseFirestore.collection(Pidgipedia.SUGGESTED_WORDS)
            .orderBy("name")
            .get(Pidgipedia.SOURCE)
            .addOnSuccessListener { querySnapshot ->
                _words.value = querySnapshot.toObjects(Word::class.java)
            }
    }

    fun addSearchedWord(word: Word) {
        viewModelScope.launch {
            addSearch(word)
        }
    }

    fun deleteAllSearches() {
        viewModelScope.launch {
            deleteAllSearchesRoom()
        }
    }

    private suspend fun getSearches(): List<Word>? {
        return withContext(Dispatchers.IO) {
            val words = wordDatabase?.getAllWordsSearch(HISTORY_LIMIT)
            words
        }
    }

    private suspend fun addSearch(word: Word) {
        withContext(Dispatchers.IO) {
            wordDatabase?.insertWordSearch(word)
        }
    }

    private suspend fun deleteAllSearchesRoom() {
        withContext(Dispatchers.IO) {
            wordDatabase?.deleteRecentSearches()
        }
    }

    private suspend fun deleteSearchById(wordId: Long) {
        withContext(Dispatchers.IO) {
            wordDatabase?.deleteRecentSearchById(wordId)
        }
    }

}