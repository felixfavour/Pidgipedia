package com.felixfavour.pidgipedia

import androidx.room.Room
import com.felixfavour.pidgipedia.entity.WordDao
import com.felixfavour.pidgipedia.entity.WordDatabase
import com.felixfavour.pidgipedia.entity.Word

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.*


@RunWith(AndroidJUnit4::class)
class WordDatabaseInstrumentationTest {

    private lateinit var wordDao: WordDao
    private lateinit var wordDatabase: WordDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        wordDatabase = Room.inMemoryDatabaseBuilder(
            context, WordDatabase::class.java).build()
        wordDao = wordDatabase.getWordDao()
    }

    @After
    fun closeDb() {
        wordDatabase.close()
    }

    @Test
    fun addWordToDatabase() {
        val word = Word(name = "OG", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = "user8", wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        )
        wordDao.insertWordSearch(word)
        val words = wordDao.getAllWordsSearch(10)
        assertTrue(words.isNotEmpty())
    }

}