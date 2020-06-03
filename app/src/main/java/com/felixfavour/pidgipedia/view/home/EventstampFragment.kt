package com.felixfavour.pidgipedia.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentEventstampBinding
import com.felixfavour.pidgipedia.util.MockData

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

        // EXTRACT SAFE ARG
        val eventStampArgument = EventstampFragmentArgs.fromBundle(requireArguments()).eventstamp
        binding.eventstamp = eventStampArgument

        // RECYCLER VIEW
        binding.commentsList.addItemDecoration(DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        ))
        binding.commentsList.adapter = EventstampCommentsAdapter().apply {
            submitList(MockData.comments)
        }

        // CARD
        Glide.with(requireContext())
            .load(R.drawable.greta)
            .centerCrop()
            .circleCrop()
            .into(binding.authorImage)

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
