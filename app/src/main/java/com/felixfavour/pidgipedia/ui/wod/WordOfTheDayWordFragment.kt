package com.felixfavour.pidgipedia.ui.wod

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.MainActivity
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.WordOfTheDayActivity.Companion.checkTime
import com.felixfavour.pidgipedia.databinding.FragmentWordOfTheDayWordBinding

/**
 * A simple [Fragment] subclass.
 */
class WordOfTheDayWordFragment : Fragment() {
    private lateinit var binding: FragmentWordOfTheDayWordBinding
    private var countDownTimer: CountDownTimer? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_word_of_the_day_word, container, false)
        val phoneWidthPixels = Resources.getSystem().displayMetrics.widthPixels
        val widthQuarter = phoneWidthPixels/4

        /*
        * Each Word Of the Day Screen should Last 30 Seconds in view
        * Here is the implementation*/
        countDownTimer = checkTime(binding.progressBarOne) {
            findNavController().navigate(WordOfTheDayWordFragmentDirections.actionWordOfTheDayWordFragmentToWordOfTheDayMeaningFragment2())
        }

        // NAVIGATIONS
        binding.constraintLayout.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (motionEvent.x <= widthQuarter) {
                        requireActivity().onBackPressed()
                    } else if (motionEvent.x >= (phoneWidthPixels-widthQuarter)) {
                        findNavController().navigate(WordOfTheDayWordFragmentDirections.actionWordOfTheDayWordFragmentToWordOfTheDayMeaningFragment2())
                    }
                }
            }
            false
        }

        binding.close.setOnClickListener {
            onDestroy()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

}
