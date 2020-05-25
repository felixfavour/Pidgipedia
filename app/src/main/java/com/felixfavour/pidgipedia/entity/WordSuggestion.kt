package com.felixfavour.pidgipedia.entity

data class WordSuggestion(
    val wordSuggested: Word,
    val suggestedBy: User,
    val timeSuggested: Long
): Eventstamp(word = wordSuggested, eventTime = timeSuggested, humanEntity = suggestedBy) {
}