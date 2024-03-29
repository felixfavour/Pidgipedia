package com.felixfavour.pidgipedia.view.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.*
import com.felixfavour.pidgipedia.databinding.FragmentHomeBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.viewmodel.HomeViewModel
import com.felixfavour.pidgipedia.viewmodel.MainActivityViewModel
import com.felixfavour.pidgipedia.viewmodel.WODViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var wodViewModel: WODViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        wodViewModel = ViewModelProvider(this).get(WODViewModel::class.java)
        homeViewModel.loadUnapprovedWords()
        homeViewModel.loadEventstamps()
    }

    @ExperimentalStdlibApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)


        // OBSERVE LIVE DATA CHANGES
        // CHECK IF USER IS AUTHENTICATED
        mainActivityViewModel.status.observe(viewLifecycleOwner, Observer {status ->
            val intent = Intent(requireContext(), AuthenticationActivity::class.java)
            if (status == FAILED) {
                sharedPreferences.edit {
                    putBoolean(Pidgipedia.AUTHENTICATION_PREFERENCES, false)
                }
                MaterialAlertDialogBuilder(context)
                    .setIcon(R.drawable.warning)
                    .setTitle(R.string.authentication_error_header)
                    .setMessage(R.string.authentication_error_message)
                    .setPositiveButton(R.string.log_in) { _, _ ->
                        startActivity(intent)
                    }.setOnDismissListener {
                        startActivity(intent)
                    }
                    .show()
            }
        })

        homeViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            binding.swipeRefreshHome.isRefreshing = true
            when (status) {
                SUCCESS -> {
                    binding.swipeRefreshHome.isRefreshing = false
                }
                FAILED -> {
                    binding.swipeRefreshHome.isRefreshing = false
                }
            }
        })


        binding.swipeRefreshHome.setOnRefreshListener {
            homeViewModel.loadEventstamps()
        }


        // Initialize DataBinding model data
        binding.homeViewModel = homeViewModel
        binding.wodViewModel = wodViewModel


        // Set LifeCycleOwner to this Fragment
        binding.lifecycleOwner = this


        // RECYCLER VIEW
        binding.unapprovedWordsList.adapter = UnapprovedWordListAdapter(OnWordClickListener { word, it ->
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordFragment(word.wordId))
        })

        binding.appUpdatesList.layoutManager = CustomLayoutManager(requireContext())

        binding.appUpdatesList.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = MARGIN
                outRect.top = MARGIN
                outRect.left = MARGIN
                outRect.right = MARGIN
            }
        })

        binding.appUpdatesList.adapter = HomeRecyclerViewAdapter(
            object: HomeRecyclerViewAdapter.HomeCardClickListener {
            override fun onHomeCardClick(view: View, eventstamp: Eventstamp) {
                when {
                    (eventstamp.badgeRewardType != null) -> {
//                        findNavController().navigate(
//                            HomeFragmentDirections.actionNavigationHomeToBadgesFragment2(eventstamp.humanEntityId!!))
                    }
                    (eventstamp.rankRewardType != null) -> {
//                        findNavController().navigate(
//                            HomeFragmentDirections.actionNavigationHomeToProfileFragment2(null, false))
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
                    HomeFragmentDirections.actionNavigationHomeToProfileFragment2(
                        eventstamp.humanEntityId, true
                    )
                )
            }

        }
        )
        var count = 0


        binding.appUpdatesList


        // NAVIGATION
        binding.suggest.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordSuggestionFragment())
        }

        if (requireActivity().intent.action == Pidgipedia.WORD_NAVIGATION) {
            val word = requireActivity().intent.getParcelableExtra<Word>(Pidgipedia.WORD)
            word?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToWordFragment(it.wordId))
            }
            requireActivity().intent.action = ""
        }


        // UI EVENT LISTENERS
        animateSuggestWordImage()
        binding.learnMore.setOnClickListener {
            val intent = Intent(requireContext(), WordOfTheDayActivity::class.java)
            startActivity(intent)
        }
        binding.wordOfTheDayCard.setOnClickListener {
            binding.learnMore.performClick()
        }

        return binding.root
    }


    private fun animateSuggestWordImage() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            binding.suggestWordImage.animate().apply {
                translationY(0f)
                duration = 300
                start()
            }
        }
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
