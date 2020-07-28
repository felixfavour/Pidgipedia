package com.felixfavour.pidgipedia.viewmodel

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.entity.WordDatabase
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.AUDIO_REFERENCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.felixfavour.pidgipedia.util.Rank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class WordViewModel(application: Application): AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val database = WordDatabase.getInstance(application.applicationContext)!!

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _bookmarked = MutableLiveData<Boolean>(false)
    val bookmarked: LiveData<Boolean>
        get() = _bookmarked

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user


    fun loadUser() {
        firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val remoteUser = documentSnapshot.toObject(RemoteUser::class.java)
                _user.value = remoteUser
            }
    }


    fun loadWord(wordId: String) {
        firebaseFirestore.collection(SUGGESTED_WORDS).document(wordId)
            .get(SOURCE)
            .addOnSuccessListener { documentSnapshot ->
                val wordSnapshot = documentSnapshot.toObject(Word::class.java)

                // Download Audio immediately after collecting [Word] data
                wordSnapshot?.let {
                    if (!it.pronunciationReference.isNullOrEmpty())
                        saveAudioLocal(it.pronunciationReference, it.wordId)
                }
                _word.value = wordSnapshot
                isWordBookmarked(wordSnapshot)
            }
    }


    fun toggleBookmarkWord() {
        /**
         * [wordLocal] is the value of [word] but the name is changed so as to bypass
         * similarity and conflict between variable names.*/
        /**
         * [bookmarkedLocal] is the value of [bookmarked] but the name is changed so as to bypass
         * similarity and conflict between variable names.*/

        val wordLocal = word.value
        val bookmarkedLocal = bookmarked.value

        if (wordLocal != null) {
            // Check If word Id is in bookmarks
            if (bookmarkedLocal != null) {
                if (!bookmarkedLocal) {
                    // Add Word Id to bookmarked Words
                    firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
                        .update("bookmarks", FieldValue.arrayUnion(wordLocal.wordId))
                        .addOnSuccessListener {
                            _bookmarked.value = true
                            loadWord(wordLocal.wordId)
                        }
                } else {
                    // Add Word Id to bookmarked Words
                    firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
                        .update("bookmarks", FieldValue.arrayRemove(wordLocal.wordId))
                        .addOnSuccessListener {
                            _bookmarked.value = false
                            loadWord(wordLocal.wordId)
                        }
                }
            }
        }
    }


    private fun isWordBookmarked(word: Word?) {

        if (word != null) {
            // Check If word Id is in bookmarks
            firebaseFirestore.collection(USERS).document(firebaseAuth.uid!!)
                .get(SOURCE)
                .addOnSuccessListener { documentSnapshot ->
                    val bookmarks = documentSnapshot["bookmarks"] as List<String>?
                    _bookmarked.value = bookmarks?.contains(word.wordId)
                }
        }

    }


    fun approveWord() {
        val userRank = user.value?.rank!!
        if (userRank <= Rank.RANK_CONTRIBUTOR) {
            firebaseFirestore.collection(SUGGESTED_WORDS).document(word.value?.wordId!!)
                .update(
                    mapOf(
                        "approved" to true,
                        "approvedAuthorId" to firebaseAuth.uid!!,
                        "lastUpdated" to System.currentTimeMillis()
                    )
                )
                .addOnSuccessListener {
                    _status.value = SUCCESS
                }
        } else {
            _status.value = FAILED
        }
    }


    fun disapproveWord() {
        val userRank = user.value?.rank!!
        if (userRank <= Rank.RANK_CONTRIBUTOR) {
            firebaseFirestore.collection(SUGGESTED_WORDS).document(word.value?.wordId!!)
                .update(
                    mapOf(
                        "rejected" to true,
                        "approvedAuthorId" to firebaseAuth.uid!!,
                        "lastUpdated" to System.currentTimeMillis()
                    )
                )
                .addOnSuccessListener {
                    _status.value = SUCCESS
                }
        } else {
            _status.value = FAILED
        }
    }

    private fun saveAudioLocal(url: String, fileName: String) {
        val context = getApplication<Application>().applicationContext
        val file = File("${context.getExternalFilesDir(null)}/$AUDIO_REFERENCE", "$fileName.m4a")

        val downloadRequest = DownloadManager.Request(Uri.parse(url))
        downloadRequest.apply {
            setAllowedOverMetered(true)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalFilesDir(context, null, "$AUDIO_REFERENCE/$fileName.m4a")
        }

        if (!file.exists()) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(downloadRequest)
        }
    }

}
