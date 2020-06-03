package com.felixfavour.pidgipedia.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.felixfavour.pidgipedia.*
import com.felixfavour.pidgipedia.databinding.FragmentHomeBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    companion object {
        const val MARGIN = 8
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setHasOptionsMenu(true)


        // Initialize DataBinding model data
        binding.homeViewModel = homeViewModel


        // Set LifeCycleOwner to this Fragment
        binding.lifecycleOwner = this


        // RECYCLER VIEW
        binding.unapprovedWordsList.adapter = UnapprovedWordListAdapter(OnWordClickListener { word, it ->
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordFragment(word))
        })

        binding.appUpdatesList.layoutManager = CustomLayoutManager(requireContext())
        binding.appUpdatesList.adapter = HomeRecyclerViewAdapter(
            object: HomeRecyclerViewAdapter.HomeCardClickListener {
            override fun onHomeCardClick(view: View, eventstamp: Eventstamp) {
                when {
                    (eventstamp.badgeRewardType != null) -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionNavigationHomeToBadgesFragment2())
                    }
                    (eventstamp.rankRewardType != null) -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionNavigationHomeToProfileFragment2(null, false))
                    }
                    else -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionNavigationHomeToEventstampFragment(eventstamp))
                    }
                }
            }

            override fun onMoreButtonClick(view: View, eventstamp: Eventstamp) {
                val bundle = Bundle().apply {
                    putParcelable(Pidgipedia.HOME_MODAL, eventstamp)
                }
                BottomSheetFragment().apply {
                    arguments = bundle
                    show(this@HomeFragment.parentFragmentManager, Pidgipedia.EVENTSTAMP)
                }
            }

            override fun onProfileImageClick(view: View, eventstamp: Eventstamp) {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToProfileFragment2(eventstamp.humanEntity, false)
                )
            }

        })


        // NAVIGATION
        binding.suggest.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordSuggestionFragment())
        }

        if (requireActivity().intent.action == Pidgipedia.WORD_NAVIGATION) {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToWordFragment(
                    requireActivity().intent.getParcelableExtra(Pidgipedia.WORD) as Word))
            requireActivity().intent.action = ""
        }


        // UI EVENT LISTENERS
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

/*
* Custom Layout manager to set make recyclerView unscrollable */
class CustomLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        val isScrollEnabled = false
        return super.canScrollVertically() && isScrollEnabled
    }
}