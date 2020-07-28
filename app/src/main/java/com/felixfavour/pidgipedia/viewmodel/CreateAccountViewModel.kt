package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.felixfavour.pidgipedia.util.Rank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions

class CreateAccountViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFunc = FirebaseFunctions.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    // LIVE DATA
    private val _creationStatus = MutableLiveData<Int>()
    val creationStatus: LiveData<Int>
        get() = _creationStatus

    private val _isUserDataUploaded = MutableLiveData<Int>()
    val isUserDataUploaded: LiveData<Int>
        get() = _isUserDataUploaded

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _isUsernameValid = MutableLiveData<Int>(Connection.FAILED)
    val isUsernameValid: LiveData<Int>
        get() = _isUsernameValid

    private val _isEmailValid = MutableLiveData<Int>(Connection.FAILED)
    val isEmailValid: LiveData<Int>
        get() = _isEmailValid


    fun createAccount(email: String, password: String, displayName: String) {
        _creationStatus.value = Connection.LOADING
        val params = hashMapOf(
            "displayName" to displayName,
            "email" to email,
            "password" to password
        )
        firebaseFunc.getHttpsCallable("createUser")
            .call(params)
            .addOnSuccessListener {
                /**
                 * Logging in after Creation of Account*/
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                    _creationStatus.value = Connection.SUCCESS
                }.addOnFailureListener {exception->
                        _error.value = exception
                        _creationStatus.value = Connection.FAILED
                    }
                firebaseAuth.currentUser?.sendEmailVerification()
            }.addOnFailureListener {exception->
                _error.value = exception
                _creationStatus.value = Connection.FAILED
            }
    }


    fun addUserFields(firstName: String, lastName: String, dateOfBirth: Long, location: String?, email: String, username: String) {
        _isUserDataUploaded.value= Connection.LOADING
        val user = RemoteUser(
            userId = firebaseAuth.currentUser?.uid!!,
            firstName = firstName,
            lastName = lastName,
            email = email,
            dateOfBirth = dateOfBirth,
            rank = Rank.RANK_JJC,
            location = location?:"",
            bio = "",
            badges = emptyList(),
            suggestedWords = emptyList(),
            approvedWords = emptyList(),
            highestScore = 0,
            profileImageURL = "",
            username = username
        )

        firebaseFirestore.collection(USERS).document(firebaseAuth.currentUser?.uid!!).set(user)
            .addOnSuccessListener {
                _isUserDataUploaded.value= Connection.SUCCESS
            }
            .addOnFailureListener { exception ->
                _error.value = exception
                _isUserDataUploaded.value= Connection.FAILED
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
