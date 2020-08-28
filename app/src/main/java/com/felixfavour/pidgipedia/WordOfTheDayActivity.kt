package com.felixfavour.pidgipedia

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.util.toast
import kotlin.math.abs

class WordOfTheDayActivity : AppCompatActivity() {

    companion object {
        private const val SECONDS = 5

        /**
         * Method to countdown exit time for each WOD screen (15 secs)
         * and to gradually increase progress bar as time increases
         * @return countDownTimer*/
        fun checkTime(progressBar: ProgressBar, action: () -> Unit) : CountDownTimer {
            val timeLimit: Long = SECONDS*1000L

            /*
            * Increase progress Bar max value to 10000 to have a smoother animation
            * A large value of 10000 and an interval of 1 means a portion of the progress
            * bar moves once every millisecond*/
            progressBar.max = 10000
            ObjectAnimator.ofInt(progressBar, "progress", 0, 10000).apply {
                duration = SECONDS*1000L
                start()
            }
            val countDownTimer = object : CountDownTimer(timeLimit, 1000) {
                override fun onFinish() {
                    action()
                }

                override fun onTick(p0: Long) {
                    // Do nothing
                }
            }
            countDownTimer.start()
            return countDownTimer
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_of_the_day)

        window.statusBarColor = ContextCompat.getColor(this, R.color.blueBlackColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primaryColorDark)

        // SET ACTIVITY TO FULL SCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun onStop() {
        super.onStop()
        finishAffinity()
    }
}
