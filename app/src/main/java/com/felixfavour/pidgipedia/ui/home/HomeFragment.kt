package com.felixfavour.pidgipedia.ui.home

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.*
import com.felixfavour.pidgipedia.databinding.*
import com.felixfavour.pidgipedia.ui.OnWordClickListener
import com.felixfavour.pidgipedia.ui.dictionary.WordListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    companion object {
        const val MARGIN = 8
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)


        //-------------------------------------------
        // Testing Object Animator
        //-------------------------------------------

        /*
        * Custom Layout manager to set make recyclerView unscrollable*/
        class CustomLayoutManager(context: Context) : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                val isScrollEnabled = false
                return super.canScrollVertically() && isScrollEnabled
            }
        }

        // RECYCLER VIEW
        binding.appUpdatesList.layoutManager = CustomLayoutManager(requireContext())
        binding.unapprovedWordsList.adapter = UnapprovedWordListAdapter(OnWordClickListener { word, it ->
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWordFragment(word))
        }).apply {
            submitList(MockData.words)
        }

        /*
        * RecyclerView item decoration to put a margin of 8dp above and below
        * every item*/
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

        binding.appUpdatesList.adapter = HomeRecyclerViewAdapter().apply {
            submitList(MainActivity.mockUpdates)
        }

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
