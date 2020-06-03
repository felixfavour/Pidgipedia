package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData

class AllWordsViewModel: ViewModel() {

    private var _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words.apply {
            loadWords()
        }

    private fun loadWords() {
        _words.value = MockData.allWords
    }

}
