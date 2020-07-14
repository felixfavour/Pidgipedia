package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.util.Pidgipedia.BOOKMARKS_VISIBILITY
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookmarksViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words

    fun loadWords() {
        firebaseFirestore.collection(SUGGESTED_WORDS)
            .whereEqualTo("bookmarked", BOOKMARKS_VISIBILITY)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                _words.value = querySnapshot.toObjects(Word::class.java)
            }
    }
}
