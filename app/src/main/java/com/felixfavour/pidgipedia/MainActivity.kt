package com.felixfavour.pidgipedia

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.User

class MainActivity : AppCompatActivity() {

    companion object {
        val mockUpdates = arrayListOf<Eventstamp>(
            Eventstamp(comment = Comment("jjhjdhkd", null, 87893783798L, arrayListOf(null)), eventTime = 938908938903),
            Eventstamp(comment = Comment("bvhkfkhf", null, 87893783798L, arrayListOf(null)), eventTime = 938908938903),
            Eventstamp(eventTime = 877379839873, isApproved = true),
            Eventstamp(eventTime = 6757668687, rankRewardType = 1),
            Eventstamp(eventTime = 100897777987, rankRewardType = 1),
            Eventstamp(eventTime = 876876786787, badgeRewardType = 2),
            Eventstamp(eventTime = 876878, badgeRewardType = 2),
            Eventstamp(eventTime = 877379839873, isApproved = true)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toolbarLogo = findViewById<ImageView>(R.id.toolbar_logo)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dictionary,
            R.id.navigation_quiz
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
