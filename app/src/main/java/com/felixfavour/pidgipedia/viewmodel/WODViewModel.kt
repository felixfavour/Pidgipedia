package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData

class WODViewModel: ViewModel() {

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word.apply {
            loadWordOfTheDay()
        }

    private fun loadWordOfTheDay() {
        _word.value = MockData.word
    }

}
