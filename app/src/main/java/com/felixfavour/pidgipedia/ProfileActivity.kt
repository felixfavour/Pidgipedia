package com.felixfavour.pidgipedia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.felixfavour.pidgipedia.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<Toolbar>(R.id.profile_activity_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val navController = Navigation.findNavController(findViewById(R.id.profile_nav_host_fragment))
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.profileFragment,
            R.id.editProfileFragment,
            R.id.badgesFragment
        ))
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
