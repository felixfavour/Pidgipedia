package com.felixfavour.pidgipedia.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordSuggestion(
    val wordSuggested: Word,
    val suggestedBy: User,
    val timeSuggested: Long,
    val suggestionComments: ArrayList<Comment>
): Eventstamp(
    word = wordSuggested,
    eventTime = timeSuggested,
    humanEntity = suggestedBy,
    isSuggested = true,
    comments = suggestionComments
) {
}