package com.felixfavour.pidgipedia.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.ProfileActivity
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.SettingsActivity
import com.felixfavour.pidgipedia.WordOfTheDayActivity
import com.felixfavour.pidgipedia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)

        // NAVIGATIONS
        binding.suggest.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordSuggestionFragment())
        }
        binding.learnMore.setOnClickListener {
            val intent = Intent(requireContext(), WordOfTheDayActivity::class.java)
            startActivity(intent)
        }
        binding.wordOfTheDayCard.setOnClickListener {
            binding.learnMore.performClick()
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
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToBookmarksFragment())
                    }
                }
                false
            }
        }
    }

}
