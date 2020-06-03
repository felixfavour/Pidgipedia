package com.felixfavour.pidgipedia.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentBadgesBinding
import com.felixfavour.pidgipedia.util.Badges
import com.felixfavour.pidgipedia.viewmodel.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class BadgesFragment : Fragment() {
    private lateinit var binding: FragmentBadgesBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_badges, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.profile = viewModel


        return binding.root

    }

    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer?.visibility = View.GONE
    }

}
