package com.felixfavour.pidgipedia.view.profile

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileBinding
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private var userArgs: User? = null
    private var isAuthor: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        bottomSheet = BottomSheetBehavior.from(binding.bottomsheet)
        userArgs = ProfileFragmentArgs.fromBundle(requireArguments()).user
        isAuthor = ProfileFragmentArgs.fromBundle(requireArguments()).isAuthor


        // BIND XML DATA
        profileViewModel.user.observe(viewLifecycleOwner, Observer {user->
            /**
             * IF FragmentArgs have not been passed to bundle use default [User] value*/
            if (userArgs == null)
                binding.user = user
            else
                binding.user = userArgs
        })


        // IF USER IS AN ARBITRARY AUTHOR AND NOT THE APP USER
        if (isAuthor) {
            hideUserProfileFields()
        }


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        /*
        * All Bottom Sheet UI activities
        * Encapsulated in a function for the purpose os seperation of concerns*/
        bottomSheetUIActions()


        // NAVIGATION
        binding.editProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
        binding.goToBadges.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToBadgesFragment())
        }


        return binding.root
    }


    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer?.visibility = View.GONE
    }


    private fun bottomSheetUIActions() {

        // Click listener to activate originally hidden bottom sheet
        binding.profileToolbar.setOnMenuItemClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            false
        }

        // Click on the Bottom Sheet Content Scrim to clear the bottom Sheet
        binding.bottomSheetShade.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheet.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // nothing here
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.editProfile.hide()
                        binding.bottomSheetShade.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.editProfile.show()
                        binding.bottomSheetShade.visibility = View.INVISIBLE
                    }
                }
            }
        })

    }


    /**
     * Method to hide fields that are not supposed to be seen by an unauthenticated [User]*/
    private fun hideUserProfileFields() {
        binding.profileEmail.visibility = View.GONE
        binding.emailAddressDummy.visibility = View.GONE
        binding.emailDivider.visibility = View.GONE
        binding.emailIcon.visibility = View.GONE
        binding.profileDob.visibility = View.GONE
        binding.dobDummy.visibility = View.GONE
        binding.dobDivider.visibility = View.GONE
        binding.dobIcon.visibility = View.GONE
        binding.goToBadges.visibility = View.GONE
        binding.editProfile.visibility = View.GONE
        binding.profileToolbar.menu.removeItem(R.id.profile_add_photo)
    }

}
