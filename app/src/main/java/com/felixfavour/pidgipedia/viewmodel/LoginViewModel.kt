package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Connection
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    // LIVE DATA
    private val _loginStatus = MutableLiveData<Int>()
    val loginStatus: LiveData<Int>
        get() = _loginStatus


    /**
     * Method to verify user's details with server database and sign In*/
    fun authenticateUserEmail(email: String, password: String) {
        _loginStatus.value = Connection.LOADING
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                _loginStatus.value = Connection.SUCCESS
            }.addOnFailureListener {
                _loginStatus.value = Connection.FAILED
            }
    }


    fun authenticateUserGoogle() {

    }


}
