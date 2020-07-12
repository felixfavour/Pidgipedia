package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BottomSheetViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _eventstamp = MutableLiveData<Eventstamp>()
    val eventstamp: LiveData<Eventstamp>
        get() = _eventstamp

    private val _humanEntity = MutableLiveData<RemoteUser>()
    val humanEntity: LiveData<RemoteUser>
        get() = _humanEntity


    fun loadEventstamp(eventstamp: Eventstamp) {
        _eventstamp.value = eventstamp
        loadUser(eventstamp.humanEntityId!!)
    }

    private fun loadUser(authorId: String) {
        firebaseFirestore.collection(USERS).document(authorId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(RemoteUser::class.java)
                _humanEntity.value = user
            }
    }
}
