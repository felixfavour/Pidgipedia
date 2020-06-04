package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.databinding.FragmentBottomSheetBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.view.home.HomeFragmentDirections
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.view.home.EventstampFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bottom_sheet,
            container,
            false
        )
        val eventstamp = arguments?.get(Pidgipedia.HOME_MODAL) as Eventstamp?
        binding.eventstamp = eventstamp

        // UI Additions
        Glide.with(requireContext())
            .load(eventstamp!!.humanEntity!!.profileImageUrl)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.person_outline)
            .into(binding.authorImage)


        binding.seeAuthor.setOnClickListener {
            try {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToProfileFragment2(eventstamp?.humanEntity, true)
                )
            }
            catch (ex: IllegalArgumentException) {
                findNavController().navigate(
                    EventstampFragmentDirections.actionEventstampFragmentToProfileFragment2(eventstamp?.humanEntity, true))
            }
            this.dismiss()
        }

        binding.seeWord.setOnClickListener {
            try {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToWordFragment(eventstamp?.word!!)
                )
            }
            catch (ex: IllegalArgumentException) {
                findNavController().navigate(
                    EventstampFragmentDirections.actionEventstampFragmentToWordFragment(eventstamp?.word!!))
            }
            this.dismiss()
        }

        return binding.root
    }

}
