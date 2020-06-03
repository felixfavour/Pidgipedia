package com.felixfavour.pidgipedia.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.felixfavour.pidgipedia.R

/**
 * A simple [Fragment] subclass.
 */
class OnboardQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboard_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onboardContentAnimation = AnimationUtils.loadAnimation(requireContext(),
            R.anim.onboarding_content_animator
        )
        val imageView = view.findViewById<ImageView>(R.id.imageViewQuiz)
        val textView = view.findViewById<TextView>(R.id.textViewQuiz)

        // Animations for the textView and ImageView
        textView.startAnimation(onboardContentAnimation)
        imageView.startAnimation(onboardContentAnimation)

    }

}
