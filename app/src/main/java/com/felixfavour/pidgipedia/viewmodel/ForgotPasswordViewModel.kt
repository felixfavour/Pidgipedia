package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Connection
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class ForgotPasswordViewModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    // LIVE DATA
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error


    fun requestNewPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _status.value = Connection.SUCCESS
            }
            .addOnFailureListener { exception ->
                _status.value = Connection.FAILED
                _error.value = exception
            }
    }


}
