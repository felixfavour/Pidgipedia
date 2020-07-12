package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.databinding.FragmentBottomSheetBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.view.home.HomeFragmentDirections
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.view.home.EventstampFragmentDirections
import com.felixfavour.pidgipedia.viewmodel.BottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var bottomSheetViewModel: BottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)
        bottomSheetViewModel = ViewModelProvider(this).get(BottomSheetViewModel::class.java)


        val eventstamp = arguments?.get(Pidgipedia.HOME_MODAL) as Eventstamp?
        bottomSheetViewModel.loadEventstamp(eventstamp!!)

        // EVENT LISTENERS
        binding.seeAuthor.setOnClickListener {
            try {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToProfileFragment2(eventstamp.humanEntityId, true)
                )
            }
            catch (ex: IllegalArgumentException) {
                findNavController().navigate(
                    EventstampFragmentDirections.actionEventstampFragmentToProfileFragment2(eventstamp.humanEntityId, true))
            }
            this.dismiss()
        }

        binding.seeWord.setOnClickListener {
            try {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToWordFragment(eventstamp.wordId!!)
                )
            }
            catch (ex: IllegalArgumentException) {
                findNavController().navigate(
                    EventstampFragmentDirections.actionEventstampFragmentToWordFragment(eventstamp.wordId!!))
            }
            this.dismiss()
        }


        // OBSERVE LIVE DATA CHANGES
        bottomSheetViewModel.humanEntity.observe(viewLifecycleOwner, Observer {remoteUser ->
            if (remoteUser != null) {
                binding.bottomSheetViewModel = bottomSheetViewModel
            }
        })

        return binding.root
    }

}
