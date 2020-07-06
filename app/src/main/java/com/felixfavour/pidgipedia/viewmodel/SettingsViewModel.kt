package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.Language
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user.apply {
            loadUser()
        }

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _language = MutableLiveData<String>()
    val language: LiveData<String>
        get() = _language.apply {
            loadLanguage()
        }


    private fun loadUser() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(RemoteUser::class.java)
                _user.value = user
            }
            .addOnFailureListener {exception ->
                _error.value = exception
            }
    }

    private fun loadLanguage() {
        _language.value = Language.ENGLISH_UK
    }


    /*
    * MEthod to add Authentication to the app if the user1 has not been authenticated*/
    private fun addAccount() {
        TODO("Not yet implemented")
    }


    /*
    * Method to Delete all Saved Words by the User*/
    private fun deleteAllBookmarks() {
        TODO("Not yet implemented")
    }


    /*
    * Method to Delete all user1's search histories*/
    private fun deleteSearchHistories() {
        TODO("Not yet implemented")
    }


    /*Method to delete all records of the user1 from remote and local Database*/
    private fun deleteAccount() {
        TODO("Not yet implemented")
    }

    fun setHistoryCacheLimit(cacheLimit: Int) {
        TODO("Not yet implemented")
    }

    fun logUserOut() {
        firebaseAuth.signOut()
    }

}
