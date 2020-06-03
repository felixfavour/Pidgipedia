package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.MockData

class HomeViewModel : ViewModel() {

    private val _eventstamps = MutableLiveData<List<Eventstamp>>()
    val eventstamps: LiveData<List<Eventstamp>>
        get() = _eventstamps.apply {
            loadEventstamps()
        }

    private val _unapprovedWords = MutableLiveData<List<Word>>()
    val unapprovedWords: LiveData<List<Word>>
        get() = _unapprovedWords.apply {
            loadUnapprovedWords()
        }

    private fun loadEventstamps() {
        _eventstamps.value = MockData.eventStamps
    }

    private fun loadUnapprovedWords() {
        _unapprovedWords.value = MockData.words
    }

}