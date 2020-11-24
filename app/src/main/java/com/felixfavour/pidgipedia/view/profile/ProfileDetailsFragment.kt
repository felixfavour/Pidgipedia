package com.felixfavour.pidgipedia.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileDetailsBinding
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProfileDetailsBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_details, container, false)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        profileViewModel.loadUser()

        profileViewModel.user.observe(viewLifecycleOwner, Observer {remoteUser->
            binding.profileViewModel = profileViewModel

            binding.goToBadges.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragment2ToBadgesFragment2(remoteUser)
                )
            }
        })

        return binding.root
    }


    /**
     * Method to hide fields that are not supposed to be seen by an unauthenticated [User]*/
//    private fun hideUserProfileFields() {
//        binding.profileEmail.visibility = View.GONE
//        binding.emailAddressDummy.visibility = View.GONE
//        binding.emailDivider.visibility = View.GONE
//        binding.emailIcon.visibility = View.GONE
//        binding.profileDob.visibility = View.GONE
//        binding.dobDummy.visibility = View.GONE
//        binding.dobDivider.visibility = View.GONE
//        binding.dobIcon.visibility = View.GONE
//        binding.goToBadges.visibility = View.GONE
//    }
}