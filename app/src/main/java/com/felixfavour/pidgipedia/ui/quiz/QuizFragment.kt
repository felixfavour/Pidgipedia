package com.felixfavour.pidgipedia.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.felixfavour.pidgipedia.R

class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quiz, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        quizViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
