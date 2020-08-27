package com.felixfavour.pidgipedia.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.LOADING
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia.AUDIO_REFERENCE
import com.felixfavour.pidgipedia.util.Pidgipedia.SUGGESTED_WORDS
import com.felixfavour.pidgipedia.util.Pidgipedia.WORD_IMAGES_REFERENCE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage

class WordSuggestionViewModel: ViewModel() {
    private val storage = FirebaseStorage.getInstance()
    private val firestore= FirebaseFirestore.getInstance()
    private lateinit var docId: String
    private val auth = FirebaseAuth.getInstance()


    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error


    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status


    private fun uploadWordAudio(word: Word, audioUri: Uri?) {
        if (audioUri != null) {
            storage.getReference("$AUDIO_REFERENCE$docId.m4a")
                .putFile(audioUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        firestore.collection(SUGGESTED_WORDS).document(docId).update(
                            mapOf("pronunciationReference" to uri.toString())
                        )
                    }
                }
        }
    }

    private fun uploadWordImage(word: Word, image: ByteArray) {
        storage.getReference("$WORD_IMAGES_REFERENCE$docId.jpg")
            .putBytes(image)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    firestore.collection(SUGGESTED_WORDS).document(docId).update(
                        mapOf("imageReference" to uri.toString())
                    )
                }
            }
    }

    fun uploadSuggestedWord(word: Word, audioUri: Uri?, image: ByteArray) {
        _status.value = LOADING

        // Get the number of words available
        firestore.collection(SUGGESTED_WORDS).get(Source.SERVER)
            .addOnSuccessListener { querySnapshot ->
                docId = "word${querySnapshot.documents.size + 1}"
                word.wordId = docId

                firestore.collection(SUGGESTED_WORDS).document(docId).set(word)
                    .addOnSuccessListener {
                        uploadWordAudio(word, audioUri)
                        uploadWordImage(word, image)
                        _status.value = SUCCESS
                    }.addOnFailureListener { exception ->
                        _status.value = FAILED
                        _error.value = exception
                    }

            }
    }

    fun editWord(word: Word) {
        _status.value = LOADING

        firestore.collection(SUGGESTED_WORDS).document(word.wordId)
            .update(
                mapOf(
                    "name" to word.name,
                    "meaning" to word.meaning,
                    "etymology" to word.etymology,
                    "plural" to word.plural,
                    "partOfSpeech" to word.partOfSpeech,
                    "syllables" to word.syllables,
                    "syllabicDivision" to word.syllabicDivision,
                    "englishEquivalent" to word.englishEquivalent,
                    "transcription" to word.transcription,
                    "synonyms" to word.synonyms,
                    "sentences" to word.sentences,
                    "lastUpdated" to word.lastUpdated,
                    "derogatory" to word.derogatory
                )
            )
            .addOnSuccessListener { _status.value = SUCCESS }
            .addOnFailureListener { _status.value = FAILED }
    }

}
