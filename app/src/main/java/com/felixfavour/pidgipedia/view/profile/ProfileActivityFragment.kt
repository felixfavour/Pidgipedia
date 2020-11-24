package com.felixfavour.pidgipedia.view.profile

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileActivityBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.util.MARGIN
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.view.home.HomeRecyclerViewAdapter
import com.felixfavour.pidgipedia.view.home.HomeRecyclerViewAdapter.HomeCardClickListener
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel

class ProfileActivityFragment : Fragment() {
    private lateinit var binding: FragmentProfileActivityBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_activity, container, false)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        profileViewModel.loadActivities()

        profileViewModel.activities.observe(viewLifecycleOwner, Observer {
            binding.profileViewModel = profileViewModel
        })

        binding.activitiesList.adapter = HomeRecyclerViewAdapter( object: HomeCardClickListener {
            override fun onHomeCardClick(view: View, eventstamp: Eventstamp) {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragment2ToEventstampFragment(eventstamp, false)
                )
            }

            override fun onMoreButtonClick(view: View, eventstamp: Eventstamp) {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragment2ToBottomSheetFragment()
                )
            }

            override fun onProfileImageClick(view: View, eventstamp: Eventstamp) {
                // Do nothing.
            }

        })

        binding.activitiesList.addItemDecoration(object: RecyclerView.ItemDecoration() {
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

        return binding.root
    }


}