package com.felixfavour.pidgipedia.view.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentScoreScreenBinding
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.GameViewModel

/**
 * A simple [Fragment] subclass.
 */
class ScoreScreen : Fragment() {
    private lateinit var binding: FragmentScoreScreenBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_screen, container, false)
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.gameViewModel = gameViewModel


        //NAVIGATION
        binding.goToHome.setOnClickListener {
            findNavController().navigate(ScoreScreenDirections.actionScoreScreen2ToMainActivity2())
        }

        binding.playAgain.setOnClickListener {
            gameViewModel.restartGame()
            findNavController().navigate(ScoreScreenDirections.actionScoreScreen2ToGameFragment2())
        }

        return binding.root
    }

}
