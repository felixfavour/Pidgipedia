package com.felixfavour.pidgipedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentEventstampBinding

/**
 * A simple [Fragment] subclass.
 */
class EventstampFragment : Fragment() {
    private lateinit var binding: FragmentEventstampBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_eventstamp, container, false)
        val activity = requireActivity() as AppCompatActivity


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.GONE
    }

}
