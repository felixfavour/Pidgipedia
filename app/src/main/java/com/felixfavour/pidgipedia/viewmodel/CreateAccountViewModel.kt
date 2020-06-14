package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.util.snack
import com.google.android.gms.tasks.Continuation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

class CreateAccountViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFunc = FirebaseFunctions.getInstance()

    // LIVE DATA
    private val _creationStatus = MutableLiveData<Int>()
    val creationStatus: LiveData<Int>
        get() = _creationStatus

    private val _isUsernameValid = MutableLiveData<Int>(Connection.FAILED)
    val isUsernameValid: LiveData<Int>
        get() = _isUsernameValid

    private val _isEmailValid = MutableLiveData<Int>(Connection.FAILED)
    val isEmailValid: LiveData<Int>
        get() = _isEmailValid


    fun createAccount(email: String, password: String, username: String) {
        _creationStatus.value = Connection.LOADING
        val params = hashMapOf(
            "uid" to username,
            "email" to email,
            "password" to password
        )
        firebaseFunc.getHttpsCallable("createUser")
            .call(params)
            .addOnSuccessListener {
                _creationStatus.value = Connection.SUCCESS
            }.addOnFailureListener {
                _creationStatus.value = Connection.FAILED
            }
    }

    fun executionFailed() {
        _creationStatus.value = Connection.FAILED
    }

    fun isUsernameTaken(username: String) {
        val params = hashMapOf(
            "uid" to username
        )
        _isUsernameValid.value = Connection.LOADING
        firebaseFunc.getHttpsCallable("checkUID")
            .call(params)
            .addOnSuccessListener {
                /**
                 * This means that the username has already been taken*/
                _isUsernameValid.value = Connection.SUCCESS
            }.addOnFailureListener {
                /**
                 * This means that the username is still available*/
                _isUsernameValid.value = Connection.FAILED
            }
    }

    fun isEmailTaken(email: String) {
        val params = hashMapOf(
            "email" to email
        )
        _isEmailValid.value = Connection.LOADING
        firebaseFunc.getHttpsCallable("checkEmail")
            .call(params)
            .addOnSuccessListener {
                /**
                 * This means that the email has already been taken*/
                _isEmailValid.value = Connection.SUCCESS
            }.addOnFailureListener {
                /**
                 * This means that the email is still available*/
                _isEmailValid.value = Connection.FAILED
            }
    }


}
