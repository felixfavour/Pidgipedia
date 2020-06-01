package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.databinding.FragmentBottomSheetBinding
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
            .load(R.drawable.greta)
            .centerCrop()
            .circleCrop()
            .into(binding.authorImage)

        binding.seeAuthor.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(
                BottomSheetFragmentDirections.actionBottomSheetFragmentToProfileFragment2(eventstamp?.humanEntity)
            )
        }
        binding.seeWord.setOnClickListener {
            findNavController().navigate(
                BottomSheetFragmentDirections.actionBottomSheetFragmentToWordFragment(eventstamp?.word!!)
            )
        }

        return binding.root
    }

}
