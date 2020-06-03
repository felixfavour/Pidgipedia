package com.felixfavour.pidgipedia.view.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_game, container, false)
        binding.goToNextQuestion.setOnClickListener {
            findNavController().navigate(GameFragmentDirections.actionGameFragment2ToScoreScreen2())
        }
        return binding.root
    }

}
