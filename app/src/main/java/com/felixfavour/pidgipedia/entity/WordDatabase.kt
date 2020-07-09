package com.felixfavour.pidgipedia.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.felixfavour.pidgipedia.util.ListConverter
import com.felixfavour.pidgipedia.util.Pidgipedia

@TypeConverters(ListConverter::class)
@Database(entities = [Word::class, User::class, Bookmark::class], version = 1, exportSchema = true)
abstract class WordDatabase: RoomDatabase() {
    companion object {
        var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase? {
            if (INSTANCE == null) {
                return Room.databaseBuilder(
                    context,
                    WordDatabase::class.java,
                    Pidgipedia.WORD_DATABASE
                ).build()
            }
            else
                return INSTANCE
        }
    }
    abstract fun getWordDao(): WordDao

    abstract fun getBookmarkDao(): BookmarkDao
}