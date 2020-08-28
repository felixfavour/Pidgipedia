package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions

class WODViewModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFunc = FirebaseFunctions.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word.apply {
            loadWordOfTheDay()
        }

    private fun loadWordOfTheDay() {
       firebaseFirestore.collection(SUGGESTED_WORDS).document("word10")
           .get(SOURCE)
           .addOnSuccessListener { documentSnapshot ->
               _word.value = documentSnapshot.toObject(Word::class.java)
           }
    }

}
