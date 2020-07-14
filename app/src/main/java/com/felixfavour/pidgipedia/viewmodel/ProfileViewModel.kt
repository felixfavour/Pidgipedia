package com.felixfavour.pidgipedia.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.PROFILE_IMAGES_REFERENCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream

class ProfileViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error


    fun loadUser() {
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

    fun loadAuthor(authorId: String) {
        firebaseFirestore.collection(USERS).document(authorId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(RemoteUser::class.java)
                _user.value = user
            }
            .addOnFailureListener {exception ->
                _error.value = exception
            }
    }


    fun editUserProfile(bio: String, firstName: String, lastName: String, dateOfBirth: Long, location: String?) {
        val user = hashMapOf<String, Any>(
            "bio" to bio,
            "firstName" to firstName,
            "lastName" to lastName,
            "dateOfBirth" to dateOfBirth,
            "location" to location!!
        )
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!).update(user)
            .addOnSuccessListener {
                _status.value = SUCCESS
            }
    }

    fun uploadProfilePicture(imageStream: InputStream?, bitmap: ByteArray?) {
        if (imageStream != null) {
            firebaseStorage.getReference(PROFILE_IMAGES_REFERENCE+firebaseAuth.uid!!)
                .putStream(imageStream).addOnSuccessListener { taskSnapshot ->
                    _status.value = SUCCESS
                    taskSnapshot.storage.downloadUrl
                        .addOnSuccessListener {downloadUrl ->
                        updatePhotoURL(downloadUrl)
                    }
                }
                .addOnFailureListener { exception ->
                    _status.value = FAILED
                    _error.value = exception
                }
        } else if (bitmap != null) {
            firebaseStorage.getReference(PROFILE_IMAGES_REFERENCE+firebaseAuth.uid!!)
                .putBytes(bitmap).addOnSuccessListener { taskSnapshot ->
                    _status.value = SUCCESS
                    taskSnapshot.storage.downloadUrl
                        .addOnSuccessListener {downloadUrl ->
                            updatePhotoURL(downloadUrl)
                        }
                }
                .addOnFailureListener { exception ->
                    _status.value = FAILED
                    _error.value = exception
                }
        }
        else {
            _status.value = FAILED
        }
    }

    private fun updatePhotoURL(downloadUrl: Uri) {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .update("profileImageURL", downloadUrl.toString()).addOnSuccessListener {
                loadUser()
            }
    }

    fun deleteProfileImage() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .update("profileImageURL", "").addOnSuccessListener {
                loadUser()
                _status.value = SUCCESS
            }
    }

}
