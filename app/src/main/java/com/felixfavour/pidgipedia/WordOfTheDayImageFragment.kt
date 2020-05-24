package com.felixfavour.pidgipedia

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.databinding.FragmentWordOfTheDayImageBinding

/**
 * A simple [Fragment] subclass.
 */
class WordOfTheDayImageFragment : Fragment() {
    private lateinit var binding: FragmentWordOfTheDayImageBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_of_the_day_image, container, false)
        val phoneWidthPixels = Resources.getSystem().displayMetrics.widthPixels
        val widthQuarter = phoneWidthPixels/4

        // NAVIGATIONS
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

        // ROUND IMAGE CORNERS
        Glide.with(requireContext())
            .load(R.drawable.greta)
            .apply(RequestOptions().transform(RoundedCorners(128)))
            .into(binding.wordImage)

        return binding.root
    }

}
