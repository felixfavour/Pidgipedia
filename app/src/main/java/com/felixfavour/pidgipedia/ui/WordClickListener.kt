package com.felixfavour.pidgipedia.ui

import android.view.View
import com.felixfavour.pidgipedia.entity.Word

class OnWordClickListener(val wordClickAction: (word: Word, view: View) -> Unit) {
    fun onWordClick(word: Word, view: View) {
        wordClickAction(word, view)
    }
}