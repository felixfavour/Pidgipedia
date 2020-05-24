package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.databinding.FragmentBookmarksBinding

/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false)

        updateUI()

        return binding.root
    }

    /*
    * Method to update UI, specifically Views Visibility when recycler view list is empty*/
    private fun updateUI() {
        if (!binding.noBookmarksLayout.isVisible) {
            binding.noBookmarksLayout.visibility = View.VISIBLE
        }
    }

}
