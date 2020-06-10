package com.felixfavour.pidgipedia.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Word
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
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


fun setAppTheme(theme: String, context: Context) {
    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (theme) {
        AppTheme.DARK_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.DARK_THEME).apply()
        }
        AppTheme.LIGHT_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.LIGHT_THEME).apply()
        }
        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            sharedPreferences.edit().putString(THEME_PREFERENCES, AppTheme.DEFAULT).apply()
        }
    }
}

fun getAppTheme(context: Context, buttonToggleGroup: MaterialButtonToggleGroup?) {

    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (sharedPreferences.getString(THEME_PREFERENCES, AppTheme.LIGHT_THEME)) {
        AppTheme.LIGHT_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            buttonToggleGroup?.check(R.id.light_theme_selection)
        }
        AppTheme.DARK_THEME -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            buttonToggleGroup?.check(R.id.dark_theme_selection)
        }
        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            buttonToggleGroup?.check(R.id.default_theme_selection)
        }
    }

}

fun getButtonGroupSelection(context: Context, buttonToggleGroup: MaterialButtonToggleGroup?) {

    val sharedPreferences = context.getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
    when (sharedPreferences.getString(THEME_PREFERENCES, AppTheme.LIGHT_THEME)) {
        AppTheme.LIGHT_THEME -> {
            val group  =buttonToggleGroup?.get(2) as MaterialButton
            group.isChecked = true
        }
        AppTheme.DARK_THEME -> {
            val group  =buttonToggleGroup?.get(0) as MaterialButton
            group.isChecked = true
        }
        AppTheme.DEFAULT -> {
            val group  =buttonToggleGroup?.get(1) as MaterialButton
            group.isChecked = true
        }
    }

}