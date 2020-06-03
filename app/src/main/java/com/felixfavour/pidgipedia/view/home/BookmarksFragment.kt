package com.felixfavour.pidgipedia.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentBookmarksBinding
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.view.dictionary.WordListAdapter

/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_bookmarks, container, false)

        // RECYCLER VIEW
        binding.bookmarksList.adapter = WordListAdapter(OnWordClickListener { word, it ->
            findNavController().navigate(BookmarksFragmentDirections.actionBookmarksFragmentToWordFragment(word))
        }).apply {
            submitList(MockData.words)
        }
        binding.bookmarksList.addItemDecoration(
            DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        )

        updateUI()

        return binding.root
    }

    /*
    * Method to update UI, specifically Views Visibility when recycler view list is empty*/
    private fun updateUI() {
        val adapter = binding.bookmarksList.adapter as WordListAdapter
        if (adapter.itemCount > 0) {
            binding.noBookmarksLayout.visibility = View.GONE
        } else {
            binding.noBookmarksLayout.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.GONE
    }

}
