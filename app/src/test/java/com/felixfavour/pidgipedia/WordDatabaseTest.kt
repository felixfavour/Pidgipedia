package com.felixfavour.pidgipedia

import android.content.Context
import androidx.room.Room
import com.felixfavour.pidgipedia.entity.BookmarkDao
import com.felixfavour.pidgipedia.entity.QuizDao
import com.felixfavour.pidgipedia.entity.WordDao
import com.felixfavour.pidgipedia.entity.WordDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.felixfavour.pidgipedia.util.MockData
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordDatabaseReadWriteTest {
    private lateinit var wordDao: WordDao
    private lateinit var bookmarkDao: BookmarkDao
    private lateinit var quizDao: QuizDao
    private lateinit var wordDatabase: WordDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val wordDatabase = Room.inMemoryDatabaseBuilder(
            context, WordDatabase::class.java).build()
        bookmarkDao = wordDatabase.getBookmarkDao()
        wordDao = wordDatabase.getWordDao()
        quizDao = wordDatabase.getQuizDao()
    }

    @After
    fun closeDb() {
        wordDatabase.close()
    }

    @Test
    fun writeReadBookmark() {
        bookmarkDao.addBookmark(MockData.word)
        val bookmarks = bookmarkDao.getAllBookmarks()
        assertThat(bookmarks[0].word, equalTo(MockData.word))
    }

    @Test
    fun deleteBookmark() {

    }

}