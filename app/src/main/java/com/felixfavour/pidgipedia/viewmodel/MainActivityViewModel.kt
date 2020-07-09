package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.google.firebase.auth.FirebaseAuth

class MainActivityViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status.apply {
            checkAuthentication()
        }

    private fun checkAuthentication() {
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser?.uid == null) {
                _status.value = FAILED
            }
        }
    }

}
