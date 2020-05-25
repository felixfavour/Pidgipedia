package com.felixfavour.pidgipedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentWordBinding

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_word, container, false)

        // ROUND WORD IMAGE
        Glide.with(requireContext())
            .load(R.drawable.greta)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
            .into(binding.wordImage)

        // HIDE AND REVEAL VIEWS
        binding.etymologyButton.setOnClickListener {
            updateUI(binding.etymologyButton, binding.etymology)
        }
        binding.synonymsButton.setOnClickListener {
            updateUI(binding.synonymsButton, binding.synonyms)
        }
        binding.sentencesButton.setOnClickListener {
            updateUI(binding.sentencesButton, binding.sentences)
        }

        // TOGGLE VIEW ICONS
        binding.audioButton.setOnClickListener {
            toggleButtonIcon(binding.audioButton,
                R.drawable.play,
                R.drawable.pause
            )
        }
        binding.bookmarkWord.setOnClickListener {
            toggleButtonIcon(binding.bookmarkWord,
                R.drawable.bookmark_border,
                R.drawable.bookmark
            )
        }

        return binding.root
    }

    private fun toggleButtonIcon(button: ImageButton, icon1: Int, icon2: Int) {
        val iconDrawable1 = ContextCompat.getDrawable(requireContext(), icon1)
        val iconDrawable2 = ContextCompat.getDrawable(requireContext(), icon2)
        if (button.drawable != iconDrawable1) {
            button.setImageDrawable(iconDrawable2)
        } else if (button.drawable != iconDrawable2) {
            button.setImageDrawable(iconDrawable1)
        }
    }

    /*
    * Method to expand and contract views in Word screen*/
    private fun updateUI(button: Button, textView: TextView) {
        if (textView.isVisible) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }

}