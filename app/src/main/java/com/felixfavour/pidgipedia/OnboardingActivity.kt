package com.felixfavour.pidgipedia

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.felixfavour.pidgipedia.ui.onboarding.OnboardingViewpagerAdapter

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_onboarding)

        // The color code matches the primaryLightColor resource
        window.statusBarColor = Color.parseColor("#6EC6FF")

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
                        onboardingProgressBar.progress = 100
                        nextSlide.text = getString(R.string.start)
                    }
                }
            }
        })
    }
}
