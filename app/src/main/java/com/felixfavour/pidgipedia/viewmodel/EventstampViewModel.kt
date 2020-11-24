package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.RemoteUser
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.COMMENTS
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EventstampViewModel: ViewModel() {

    private lateinit var wordId: String

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _eventstamp = MutableLiveData<Eventstamp>()
    val eventstamp: LiveData<Eventstamp>
        get() = _eventstamp

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    private val _humanEntity = MutableLiveData<RemoteUser>()
    val humanEntity: LiveData<RemoteUser>
        get() = _humanEntity

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    private val _user = MutableLiveData<RemoteUser>()
    val user: LiveData<RemoteUser>
        get() = _user


    fun loadEventstamp(eventstamp: Eventstamp?) {
        _eventstamp.value = eventstamp
        loadUser()
        loadWord()
        loadHumanEntity()
    }


    private fun loadWord() {
        val wordId = _eventstamp.value?.wordId
        firebaseFirestore.collection(SUGGESTED_WORDS).document(wordId!!)
            .get(SOURCE)
            .addOnSuccessListener {suggestedWord ->
                _word.value = suggestedWord.toObject(Word::class.java)
            }
    }


    private fun loadHumanEntity() {
        val userId = eventstamp.value?.humanEntityId
        firebaseFirestore.collection(USERS).document(userId!!)
            .get(SOURCE)
            .addOnSuccessListener { user ->
                _humanEntity.value = user.toObject(RemoteUser::class.java)
            }
    }


    private fun loadUser() {
        val userId = firebaseAuth.uid
        userId?.let {
            firebaseFirestore.collection(USERS).document(it)
                .get(SOURCE)
                .addOnSuccessListener { documentSnapshot ->
                    _user.value = documentSnapshot.toObject(RemoteUser::class.java)
                }
        }
    }


    fun deleteComment(commentId: String) {
        firebaseFirestore.collection(COMMENTS).document(commentId)
            .delete()
            .addOnSuccessListener {
                if (this@EventstampViewModel::wordId.isInitialized) {
                    loadComments(wordId)
                }
            }
    }


    fun editComment(commentText: String, commentId: String) {
        firebaseFirestore.collection(COMMENTS)
            .document(commentId)
            .update("commentContent", commentText)
            .addOnSuccessListener {
                if (this@EventstampViewModel::wordId.isInitialized) {
                    loadComments(wordId)
                }
            }
    }


    fun uploadComment(commentText: String, wordId: String?) {
        firebaseFirestore.collection(COMMENTS)
            .get()
            .addOnSuccessListener {querySnapshot ->
                val commentId = "comment${querySnapshot.documents.size + 1}"
                val comment = Comment(
                    commentId = commentId,
                    commentContent = commentText,
                    dateCreated = System.currentTimeMillis(),
                    wordId = wordId!!,
                    authorId = firebaseAuth.uid!!,
                    respondingTo = _humanEntity.value?.userId!!.let {userId ->
                        if (userId == firebaseAuth.uid) {
                            return@let ""
                        }
                        return@let userId
                    }
                )
            firebaseFirestore.collection(COMMENTS).document(commentId)
                .set(comment)
                .addOnSuccessListener {
                    if (this@EventstampViewModel::wordId.isInitialized) {
                        loadComments(wordId)
                    }
                }
        }
    }


    fun loadComments(wordIdParam: String?) {
        wordId = wordIdParam!!
        val remoteComments = mutableListOf<Comment>()
        firebaseFirestore.collection(COMMENTS)
            .whereEqualTo("wordId", wordIdParam)
            .get(SOURCE)
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.documents
                documents.forEach { document ->
                    val comment = document.toObject(Comment::class.java)
                    remoteComments.add(comment!!)
                    _comments.value = remoteComments
                }
            }
    }


    fun replyComment(authorId: String?) {
        firebaseFirestore.collection(USERS).document(authorId!!)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val author = documentSnapshot.toObject(RemoteUser::class.java)
                _humanEntity.value = author
                _status.value = SUCCESS
            }
    }

}
