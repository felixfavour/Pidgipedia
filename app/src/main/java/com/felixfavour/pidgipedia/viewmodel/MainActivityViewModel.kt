package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class MainActivityViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseMessaging = FirebaseMessaging.getInstance().apply {
        isAutoInitEnabled = true
        generateFCMToken()
    }

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

    private fun generateFCMToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val token = instanceIdResult.token
        }
    }

}
