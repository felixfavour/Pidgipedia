package com.felixfavour.pidgipedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlin.math.abs

class WordOfTheDayActivity : AppCompatActivity() {

    companion object {
        const val SECONDS = 10

        /**
         * Method to countdown exit time for each WOD screen (15 secs)
         * and to gradually increase progress bar as time increases
         * @return countDownTimer*/
        fun checkTime(progressBar: ProgressBar, action: () -> Unit) : CountDownTimer {
            val timeLimit: Long = SECONDS*1000L
            val countDownTimer = object : CountDownTimer(timeLimit, 1000) {
                override fun onFinish() {
                    action()
                }

                override fun onTick(p0: Long) {
                    var second = p0.toInt()/1000
                    /*
                    * Expressing each second as a percentage of 100
                    * PURPOSE OF RE-ASSINGING `second` to the difference between
                    * it and the constant `SECOND` is to make the Countdown Timer to count in an
                    * increasing order and not decreasing
                    *
                    * It is in abs() to get the absolute value because it returns a negative value*/
                    second = abs(second - SECONDS)

                    val progressSecond = (second * 100)/SECONDS
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress(progressSecond, true)
                    } else {
                        progressBar.progress = progressSecond
                    }
                }
            }
            countDownTimer.start()
            return countDownTimer
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_of_the_day)

        // SET ACTIVITY TO FULL SCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
