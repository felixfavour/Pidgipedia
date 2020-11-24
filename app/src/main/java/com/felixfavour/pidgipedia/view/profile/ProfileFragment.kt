package com.felixfavour.pidgipedia.view.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileBinding
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.resizeImage
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.ByteArrayOutputStream
import java.lang.Exception
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

    companion object {
        const val IMAGE_REQUEST_CODE = 1
        const val REQUEST_IMAGE_CAPTURE = 2
        const val NUMBER_OF_PAGES = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        bottomSheet = BottomSheetBehavior.from(binding.bottomsheet)
        profileViewModel.loadUser()


        binding.profileViewModel = profileViewModel


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


        // PROFILE VIEW PAGER
        binding.profileViewPager.adapter = object: FragmentStateAdapter(requireActivity()) {

            override fun getItemCount(): Int {
                return NUMBER_OF_PAGES
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        ProfileDetailsFragment()
                    }
                    else -> {
                        ProfileActivityFragment()
                    }
                }
            }
        }
        binding.profileViewpagerTab.setupWithViewPager(binding.profileViewPager)


        // IF USER IS AN ARBITRARY AUTHOR AND NOT THE APP USER
//        if (isAuthor) {
//            hideUserProfileFields()
//        }


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        /*
        * All Bottom Sheet UI activities
        * Encapsulated in a function for the purpose os seperation of concerns*/
        bottomSheetUIActions()


        // NAVIGATION
        binding.editProfile.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment2(profileViewModel.user.value!!)
            )
        }


        // USER IMAGE OPERATIONS
        binding.addPictureFiles.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.addPictureCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(requireActivity().packageManager)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        binding.deletePicture.setOnClickListener {
            profileViewModel.deleteProfileImage()
        }


        // OBSERVE LIVE DATA
        profileViewModel.status.observe(viewLifecycleOwner, Observer { userRetrievalStatus ->
            if (userRetrievalStatus == SUCCESS) {
                binding.statusProgress.visibility = View.GONE
                binding.bottomSheetShade.visibility = View.GONE
            }
        })

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
        try {
            if (data != null) {
                when (requestCode) {
                    IMAGE_REQUEST_CODE -> {
                        val imageStream = requireContext().contentResolver.openInputStream(data.data!!)
                        val bitmap = BitmapFactory.decodeStream(imageStream)
                        val bitmapComp = resizeImage(bitmap)
                        val bitmapStream = ByteArrayOutputStream().also {
                            bitmapComp.compress(Bitmap.CompressFormat.JPEG, 100, it)
                        }

                        profileViewModel.uploadProfilePicture(null, bitmapStream.toByteArray())
                    }
                    REQUEST_IMAGE_CAPTURE -> {
                        val bitmap = data.extras?.get("data") as Bitmap
                        val bitmapStream = ByteArrayOutputStream().also {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                        }
                        profileViewModel.uploadProfilePicture(null, bitmapStream.toByteArray())
                    }
                }
            }
        } catch (ex: Exception) {}
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
            binding.approvedWordsList.visibility = View.GONE
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

}

private fun TabLayout.setupWithViewPager(profileViewPager: ViewPager2) {
    TabLayoutMediator(this, profileViewPager, true, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
        when (position) {
            0 -> tab.text = context.getString(R.string.about)
            1 -> tab.text = context.getString(R.string.activity)
        }
    }).attach()
}
