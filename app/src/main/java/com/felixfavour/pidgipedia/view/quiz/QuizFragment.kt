package com.felixfavour.pidgipedia.view.quiz

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.*
import com.felixfavour.pidgipedia.databinding.FragmentQuizBinding
import com.felixfavour.pidgipedia.util.Game
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.showWarningDialog
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.QuizViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.Source

class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        setHasOptionsMenu(true)


        // Bind XML data
        binding.quizViewModel = quizViewModel


        // Set LifeCycle Owner
        binding.lifecycleOwner = this


        // NAVIGATION
        // CHOOSE GAME DIFFICULTY
        binding.play.setOnClickListener {
            /*
            * This decision construct means that basically if there is no internet connection
            * game cannot be played*/
            if (SOURCE == Source.DEFAULT) {
                val intent = Intent(requireContext(), GameActivity::class.java)
                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(R.layout.dialog_game_difficulty)
                    .show()

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.findViewById<Button>(R.id.game_easy)?.setOnClickListener {
                    startActivity(intent.apply { action = Game.EASY.toString() })
                }
                dialog.findViewById<Button>(R.id.game_medium)?.setOnClickListener {
                    startActivity(intent.apply { action = Game.MEDIUM.toString() })
                }
                dialog.findViewById<Button>(R.id.game_hard)?.setOnClickListener {
                    startActivity(intent.apply { action = Game.HARD.toString() })
                }
            } else {
                showWarningDialog(requireContext(), R.string.no_internet, R.string.quiz_internet_error)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // REVEAL APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.VISIBLE

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}
