package com.felixfavour.pidgipedia.ui.quiz

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.ProfileActivity
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.SettingsActivity
import com.felixfavour.pidgipedia.databinding.FragmentQuizBinding
import com.felixfavour.pidgipedia.util.toast

class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        setHasOptionsMenu(true)

        // NAVIGATIONS
        binding.play.setOnClickListener {
            findNavController().navigate(QuizFragmentDirections.actionNavigationQuizToGameActivity())
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.basic_menu, menu)
        menu.forEach { item: MenuItem ->
            item.setOnMenuItemClickListener {
                when (item.itemId) {
                    R.id.profile -> {
                        val activityIntent = Intent(requireContext(), ProfileActivity::class.java)
                        startActivity(activityIntent)
                    }
                    R.id.settings -> {
                        val activityIntent = Intent(requireContext(), SettingsActivity::class.java)
                        startActivity(activityIntent)
                    }
                    R.id.menu_bookmarks -> {
                        findNavController().navigate(QuizFragmentDirections.actionNavigationQuizToBookmarksFragment())
                    }
                }
                false
            }
        }
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
