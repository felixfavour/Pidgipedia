package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.util.Pidgipedia.BOOKMARKS_VISIBILITY
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookmarksViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var wordsLocal: MutableList<Word> = mutableListOf()

    private val _words = MutableLiveData<List<Word>>(emptyList())
    val words: LiveData<List<Word>>
        get() = _words

    fun loadWords() {
        /**
         * [wordsLocal] is a mutable list to add the value of each word generated from their
         * respective [wordIds] as they are collected from the remote server
         */
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val wordIds = documentSnapshot["bookmarks"] as List<String>?
                wordIds?.forEach { wordId ->
                    firebaseFirestore.collection(SUGGESTED_WORDS).document(wordId)
                        .get(SOURCE)
                        .addOnSuccessListener { documentSnapshot ->
                            wordsLocal.add(documentSnapshot.toObject(Word::class.java)!!)
                            _words.value = wordsLocal
                        }
                }
            }
    }
}
