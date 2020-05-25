package com.felixfavour.pidgipedia.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentScoreScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class ScoreScreen : Fragment() {
    private lateinit var binding: FragmentScoreScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_score_screen, container, false)

        //NAVIGATIONS
        binding.goToHome.setOnClickListener {
            findNavController().navigate(ScoreScreenDirections.actionScoreScreen2ToMainActivity2())
        }

        return binding.root
    }

}