package com.felixfavour.pidgipedia.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Word
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

const val THEME_PREFERENCES = "THEME PREFERENCES"

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun snack(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

@ExperimentalStdlibApi
fun shareWord(context: Context, word: Word) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(
            Intent.EXTRA_TEXT,
            "${word.name.capitalize(Locale.getDefault())}\n${word.meaning}." +
                    "\n" + context.getString(R.string.download_app_for_more)
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}