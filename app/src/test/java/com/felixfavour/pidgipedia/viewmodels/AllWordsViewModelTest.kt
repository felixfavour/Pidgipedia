package com.felixfavour.pidgipedia.viewmodels

import androidx.lifecycle.Observer
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.viewmodel.AllWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

class AllWordsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: AllWordsViewModel
    val observer: Observer<List<Word>> = mock()
    val firebaseFirestore: FirebaseFirestore = mock()

    @Before
    fun setUp() {
        viewModel = AllWordsViewModel()
        viewModel.words.observeForever(observer)
    }

    @Test
    fun testWords() {
        whenever(
            firebaseFirestore.collection(Pidgipedia.SUGGESTED_WORDS)
                .orderBy("name")
                .get(Pidgipedia.SOURCE)
        ).thenReturn(null)

        viewModel.loadWords()

        val captor = ArgumentCaptor.forClass(Word::class.java)
        captor.run {

        }
        assertTrue(viewModel.words.hasObservers())
    }
}