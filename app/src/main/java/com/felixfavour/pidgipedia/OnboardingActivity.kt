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
import android.widget.Button
import android.widget.ProgressBar
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
        sharedPreferences = getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)

        isActivityOpenedBefore(applicationContext)

        createNotificationChannel(applicationContext)

        checkInternetConnectivity()

        // The color code matches the primaryLightColor resource
        window.statusBarColor = Color.parseColor("#6EC6FF")
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
            setContentView(R.layout.activity_onboarding)
            onboardingUI()
            sharedPref.edit().putBoolean(ONBOARDING_PREFERENCE, true).commit()
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

    private fun checkInternetConnectivity() {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Pidgipedia.SOURCE = Source.DEFAULT
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Pidgipedia.SOURCE = Source.CACHE
                    findViewById<View>(R.id.main_activity_layout).apply {
                        if (this != null)
                            snack(this, getString(R.string.no_internet_access))
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Pidgipedia.SOURCE = Source.CACHE
                    findViewById<View>(R.id.onboarding_activity_layout).apply {
                        if (this != null)
                            snack(this, getString(R.string.internet_unstable))
                    }
                }
            }, 5000)
        } else {
            connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Pidgipedia.SOURCE = Source.DEFAULT
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Pidgipedia.SOURCE = Source.CACHE
                    findViewById<View>(R.id.onboarding_activity_layout).apply {
                        if (this != null)
                            snack(this, context.getString(R.string.internet_unstable))
                    }
                }
            })
        }
    }

    private fun onboardingUI() {

        val onboardingViewpager = findViewById<ViewPager2>(R.id.onboarding_viewpager)
        val onboardingProgressBar = findViewById<ProgressBar>(R.id.onboardingProgressBar)
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            onboardingProgressBar.setProgress(33, true)
                        }
                        onboardingProgressBar.progress = 33
                        nextSlide.text = getString(R.string.more_tori)
                    }
                    1 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            onboardingProgressBar.setProgress(66, true)
                        }
                        onboardingProgressBar.progress = 66
                        nextSlide.text = getString(R.string.more_tori)
                    }
                    else -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            onboardingProgressBar.setProgress(100, true)
                        }
                        val sharedPref = applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                        sharedPref.edit {
                            putBoolean(ONBOARDING_PREFERENCE, true)
                        }
                        onboardingProgressBar.progress = 100
                        nextSlide.text = getString(R.string.start)
                    }
                }
            }
        })
    }
}
