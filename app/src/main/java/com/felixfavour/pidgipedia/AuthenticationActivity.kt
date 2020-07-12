package com.felixfavour.pidgipedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixfavour.pidgipedia.util.toast

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_authentication)
    }
}
