package com.felixfavour.pidgipedia.view.dictionary

import android.view.View
import com.felixfavour.pidgipedia.entity.Word

abstract class OnWordLongClickListener() {
    abstract fun onLongClick(view: View, word: Word)
}
