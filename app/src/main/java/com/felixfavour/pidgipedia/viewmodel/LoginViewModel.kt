package com.felixfavour.pidgipedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.util.Language
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(): ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    // LIVE DATA
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _language = MutableLiveData<String>(Language.ENGLISH_UK)
    val language: LiveData<String>
        get() = _language


    /**
     * Method to verify user's details with server database and sign In*/
    fun authenticateUserEmail(email: String, password: String) {
        if (email.isNotBlank() || password.isNotBlank()) {
            _status.value = Connection.LOADING
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    _status.value = Connection.SUCCESS
                }.addOnFailureListener {exception ->
                    _status.value = Connection.FAILED
                    _error.value = exception
                }
        }
    }


    fun authenticateUserGoogle(googleSignInAccount: GoogleSignInAccount) {
        _status.value = Connection.LOADING

        val credential = GoogleAuthProvider.getCredential(
            googleSignInAccount.idToken,
            null
        )
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {authResult ->
            _status.value = Connection.SUCCESS
        }.addOnFailureListener { exception ->
            _status.value = Connection.FAILED
            _error.value = exception
        }

    }

    fun setLanguage(language: String) {
        when (language) {

        }
    }

}
