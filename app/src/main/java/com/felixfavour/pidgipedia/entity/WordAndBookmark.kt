package com.felixfavour.pidgipedia.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WordAndBookmark(
    @Embedded
    val word: Word,
    @Relation(parentColumn = "word_id", entityColumn = "bookmark_word_id")
    val bookmark: Bookmark
)