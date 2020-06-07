package com.felixfavour.pidgipedia.view.game

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentGameBinding
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    /**
     * So as to ensure that various callback responses of the [RadioGroup] OnCheckedChangeListener
     * are not sent to the ViewModel, [answerPicked] was created and would be sent to the viewModel*/
    private var answerPicked = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.gameViewModel = gameViewModel


        // QUESTION COUNTDOWN
        val timer = object: CountDownTimer(15 * 1000L, 1000) {
            override fun onTick(p0: Long) {
                gameViewModel.updateTimer(p0)
            }

            override fun onFinish() {
                binding.goToNextQuestion.performClick()
            }
        }

        timer.start()

        // NAVIGATION
        gameViewModel.loadQuiz()

        binding.goToNextQuestion.setOnClickListener {
            timer.cancel()
            timer.start()
            /** Method that receives [answerPicked] in viewModel to validate answer*/
            gameViewModel.isAnswerCorrect(answerPicked)
            /** Method to bring in new Question
             * Main action done by the Click event*/
            gameViewModel.loadQuiz()
            /** Clear checks in previous question before opening new one*/
            binding.answersGroup.check(-1)
        }


        // OBSERVE LIVEDATA CHANGES
        gameViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { currentQuestion ->
            /**
             * When current question is just one step from the end, make the next button
             * text turn to finish and change Navigation destination to Score Screen*/
            if (currentQuestion == (GameViewModel.TOTAL_QUESTIONS-1)) {
                binding.goToNextQuestion.setOnClickListener {
                    gameViewModel.isAnswerCorrect(answerPicked)
                    try {
                        findNavController().navigate(GameFragmentDirections.actionGameFragment2ToScoreScreen2())
                    } catch (ex: IllegalArgumentException) {
                        /**Exception is thrown as a result of the [timer] onFinish() method
                         * that tries to navigate to Score Screen when it is already there.
                         * This exception is likely to be thrown when the user is manipulating the
                         * next button not when it is controlled by the timer.*/
                    }
                }
                binding.goToNextQuestion.text = getString(R.string.finish)
            }
        })

        binding.answersGroup.setOnCheckedChangeListener { _, id ->
            answerPicked = id
        }


        return binding.root
    }

}
