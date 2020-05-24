package com.felixfavour.pidgipedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WordOfTheDayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_of_the_day)

        // SET ACTIVITY TO FULL SCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
