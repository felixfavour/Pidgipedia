package com.felixfavour.pidgipedia

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.databinding.FragmentWordOfTheDayWordBinding

/**
 * A simple [Fragment] subclass.
 */
class WordOfTheDayWordFragment : Fragment() {
    private lateinit var binding: FragmentWordOfTheDayWordBinding
    private var displayMetrics: DisplayMetrics? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_of_the_day_word, container, false)
        val phoneWidthPixels = Resources.getSystem().displayMetrics.widthPixels
        val widthQuarter = phoneWidthPixels/4

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
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}
