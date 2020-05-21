package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.databinding.FragmentProfileBinding
import jp.wasabeef.glide.transformations.BitmapTransformation
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profileToolbar.title = "Greta Thunberg"

        // Round profile picture image
        Glide.with(requireContext())
            .load(requireContext().getDrawable(R.drawable.greta))
            .circleCrop()
            .into(binding.profileImage)

        // Glide: Set the blurred background image
        Glide.with(requireContext())
            .load(requireContext().getDrawable(R.drawable.greta))
            .transform(BlurTransformation(2, 3))
            .into(binding.profileImageBg)

        // NAVIGATIONS
        binding.editProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
        binding.goToBadges.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToBadgesFragment())
        }

        return binding.root
    }

}
