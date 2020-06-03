package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData

class DictionaryViewModel : ViewModel() {

    private val _recentSearches = MutableLiveData<List<Word>>()
    val recentSearches: LiveData<List<Word>>
        get() = _recentSearches.apply {
            loadRecentSearches()
        }

    private fun loadRecentSearches() {
        _recentSearches.value = listOf()
    }

}