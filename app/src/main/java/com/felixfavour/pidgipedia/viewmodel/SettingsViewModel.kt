package com.felixfavour.pidgipedia.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.util.Language
import com.felixfavour.pidgipedia.util.Notification
import com.felixfavour.pidgipedia.util.NotificationsCode
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class SettingsViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>().applicationContext

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseMessaging = FirebaseMessaging.getInstance()
    private val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user.apply {
            loadUser()
        }

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _isSubscribed = MutableLiveData<String>()
    val isSubscribed: LiveData<String>
        get() = _isSubscribed

    private val _isUnsubscribed = MutableLiveData<String>()
    val isUnubscribed: LiveData<String>
        get() = _isUnsubscribed

    private val _language = MutableLiveData<String>()
    val language: LiveData<String>
        get() = _language.apply {
            loadLanguage()
        }


    private fun loadUser() {
        if (firebaseAuth.uid != null) {
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
    }


    private fun loadLanguage() {
        _language.value = Language.ENGLISH_UK
    }


    fun logUserOut() {
        firebaseAuth.signOut()
    }


    fun subscribeNotification(notif: String) {
        firebaseMessaging.subscribeToTopic(notif)
            .addOnSuccessListener {
                _isSubscribed.value = notif
                sharedPreferences.edit { putBoolean(notif, true) }
            }.addOnFailureListener {
                sharedPreferences.edit { putBoolean(notif, false) }
            }
    }


    fun unsubscribeNotification(notif: String) {
        firebaseMessaging.unsubscribeFromTopic(notif)
            .addOnSuccessListener {
                _isUnsubscribed.value = notif
                sharedPreferences.edit { putBoolean(notif, false) }
            }.addOnFailureListener {
                sharedPreferences.edit { putBoolean(NotificationsCode.COMMENTS, true) }
            }
    }

}
