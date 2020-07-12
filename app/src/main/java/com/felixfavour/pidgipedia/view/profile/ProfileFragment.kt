package com.felixfavour.pidgipedia.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileBinding
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.view.home.WordSuggestionFragment.Companion.IMAGE_REQUEST_CODE
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private var userId: String? = null
    private var isAuthor: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        bottomSheet = BottomSheetBehavior.from(binding.bottomsheet)


        binding.profileViewModel = profileViewModel
        profileViewModel.loadUser()


        // GET ARGUMENTS PASSED THROUGH NAVIGATION COMPONENTS
        try {
            userId = ProfileFragmentArgs.fromBundle(requireArguments()).userId
            isAuthor = ProfileFragmentArgs.fromBundle(requireArguments()).isAuthor
        } catch (ex: IllegalArgumentException) {
            userId = null
            isAuthor = false
        }


        // BIND XML DATA
        profileViewModel.user.observe(viewLifecycleOwner, Observer {user->
            /**
             * IF FragmentArgs have not been passed to bundle use default [User] value*/
            if (userId != null)
                profileViewModel.loadAuthor(userId!!)
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
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(profileViewModel.user.value!!)
            )
        }
        binding.goToBadges.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToBadgesFragment(profileViewModel.user.value!!))
        }


        // USER IMAGE OPERATIONS
        binding.addPictureFiles.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.deletePicture.setOnClickListener {
            profileViewModel.deleteProfileImage()
        }


        // OBSERVE LIVE DATA
        profileViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status == SUCCESS) {
                snack(requireView(), getString(R.string.profile_image_updation))
                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        profileViewModel.error.observe(viewLifecycleOwner, Observer { exception ->
            if (exception != null) {
                snack(requireView(), exception.localizedMessage!!)
                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })


        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE) {
            val imageStream = requireContext().contentResolver.openInputStream(data?.data!!)
            profileViewModel.uploadProfilePicture(imageStream)
        }
    }


    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // HIDE APP LOGO
        val activityMain = requireActivity() as AppCompatActivity
        val appLogoContainer = activityMain.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
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
