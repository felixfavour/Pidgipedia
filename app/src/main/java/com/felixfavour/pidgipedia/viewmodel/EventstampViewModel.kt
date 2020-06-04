package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.util.MockData

class EventstampViewModel: ViewModel() {

    private val _eventstamp = MutableLiveData<Eventstamp>()
    val eventstamp: LiveData<Eventstamp>
        get() = _eventstamp


    fun loadEventstamp(eventstamp: Eventstamp?) {
        _eventstamp.value = eventstamp
    }


}
