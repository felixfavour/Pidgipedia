package com.felixfavour.pidgipedia.view.wod

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.MainActivity
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.WordOfTheDayActivity.Companion.checkTime
import com.felixfavour.pidgipedia.databinding.FragmentWordOfTheDayImageBinding
import com.felixfavour.pidgipedia.util.shareWord
import com.felixfavour.pidgipedia.viewmodel.WODViewModel

/**
 * A simple [Fragment] subclass.
 */
class WordOfTheDayImageFragment : Fragment() {
    private lateinit var binding: FragmentWordOfTheDayImageBinding
    private lateinit var viewModel: WODViewModel
    private var countDownTimer: CountDownTimer? = null

    @ExperimentalStdlibApi
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_of_the_day_image, container, false)
        viewModel = ViewModelProvider(this).get(WODViewModel::class.java)
        val phoneWidthPixels = Resources.getSystem().displayMetrics.widthPixels
        val widthQuarter = phoneWidthPixels/4


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.viewModel = viewModel


        // NAVIGATION
        /*
        * Each Word Of the Day Screen should Last 30 Seconds in view
        * Here is the implementation*/
        countDownTimer = checkTime(binding.progressBarThree) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.constraintLayout.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (motionEvent.x <= widthQuarter) {
                        requireActivity().onBackPressed()
                    } else if (motionEvent.x >= (phoneWidthPixels-widthQuarter)) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            false
        }

        viewModel.word.observe(viewLifecycleOwner, Observer { word->
            // SHARE WORD ON BUTTON CLICKED
            binding.shareWord.setOnClickListener {
                shareWord(requireContext(), word)
            }
        })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

}
