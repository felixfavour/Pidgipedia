package com.felixfavour.pidgipedia

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.util.Pidgipedia.ONBOARDING_PREFERENCE
import com.felixfavour.pidgipedia.util.Pidgipedia.PREFERENCES
import com.felixfavour.pidgipedia.view.authentication.LoginFragmentDirections
import com.felixfavour.pidgipedia.view.onboarding.OnboardingViewpagerAdapter
import com.google.firebase.firestore.Source

class OnboardingActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

        isActivityOpenedBefore(applicationContext)

        onboardingUI()

        createNotificationChannel(applicationContext)

        checkInternetConnectivity(applicationContext)

        // The color code matches the primaryLightColor resource
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.primaryColorLightConstant)
    }

    /*
    * Function to implement the Onboarding function.
    * It makes this Activity Show once - only after installation
    */
    private fun isActivityOpenedBefore(applicationContext: Context) {
        val sharedPref = applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(ONBOARDING_PREFERENCE, false)) {
            // CHECK IF USER IS AUTHENTICATED
            isUserAuthenticated()
            getAppTheme(applicationContext, null)
        } else {
            onboardingUI()
        }
    }

    private fun isUserAuthenticated() {
        if(sharedPreferences.getBoolean(Pidgipedia.AUTHENTICATION_PREFERENCES, false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun onboardingUI() {
        setContentView(R.layout.activity_onboarding)
        val clouds = findViewById<ImageView>(R.id.clouds)
        val cloudAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.onboarding_cloud_animation)
        clouds.animation = cloudAnimation
        clouds.animate()

        val onboardingViewpager = findViewById<ViewPager2>(R.id.onboarding_viewpager)
        val circle1 = findViewById<ImageView>(R.id.circle1)
        val circle2 = findViewById<ImageView>(R.id.circle2)
        val circle3 = findViewById<ImageView>(R.id.circle3)
        val nextSlide = findViewById<Button>(R.id.onboardingButton)


        onboardingViewpager.adapter = OnboardingViewpagerAdapter(this)

        nextSlide.setOnClickListener {
            when (onboardingViewpager.currentItem) {
                0 -> onboardingViewpager.setCurrentItem(1, true)
                1 -> onboardingViewpager.setCurrentItem(2, true)
                2 -> {
                    val activityIntent = Intent(this, AuthenticationActivity::class.java)
                    startActivity(activityIntent)
                }
            }
        }

        onboardingViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        tintCircle(circle1)
                        nextSlide.text = getString(R.string.more_tori)
                    }
                    1 -> {
                        tintCircle(circle2)
                        nextSlide.text = getString(R.string.more_tori)
                    }
                    else -> {
                        tintCircle(circle3)
                        val sharedPref = applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                        sharedPref.edit {
                            putBoolean(ONBOARDING_PREFERENCE, true)
                        }
                        nextSlide.text = getString(R.string.start)
                    }
                }
            }
        })
    }

    private fun tintCircle(circle: ImageView) {
        val circle1 = findViewById<ImageView>(R.id.circle1)
        val circle2 = findViewById<ImageView>(R.id.circle2)
        val circle3 = findViewById<ImageView>(R.id.circle3)

        when (circle) {
            circle1 -> {
                circle1.setColorFilter(ContextCompat.getColor(applicationContext, R.color.primaryWhiteColor))
                circle2.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
                circle3.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
            }
            circle2 -> {
                circle2.setColorFilter(ContextCompat.getColor(applicationContext, R.color.primaryWhiteColor))
                circle1.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
                circle3.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
            }
            circle3 -> {
                circle3.setColorFilter(ContextCompat.getColor(applicationContext, R.color.primaryWhiteColor))
                circle2.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
                circle1.setColorFilter(ContextCompat.getColor(applicationContext, R.color.secondaryGreyColor))
            }
        }
    }
}
