package com.felixfavour.pidgipedia.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var bottomSheet : BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        bottomSheet = BottomSheetBehavior.from(binding.bottomsheet)

        /*
        * All Bottom Sheet UI activities
        * Encapsulated in a function for the purpose os seperation of concerns*/
        bottomSheetUIActions()

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

    private fun bottomSheetUIActions() {

        // Click listener to activate originally hidden bottom sheet

        binding.profileToolbar.setOnMenuItemClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            false
        }


        binding.bottomSheetShade.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheet.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // nothing yet
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
