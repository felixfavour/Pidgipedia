package com.felixfavour.pidgipedia.view.dictionary

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentDictionaryBinding
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.viewmodel.DictionaryViewModel

class DictionaryFragment : Fragment() {

    private lateinit var dictionaryViewModel: DictionaryViewModel
    private lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary, container, false)
        dictionaryViewModel = ViewModelProvider(this).get(DictionaryViewModel::class.java)
        setHasOptionsMenu(true)


        // Bind XML data
        binding.dictionaryViewModel = dictionaryViewModel


        // Set Lifecycle Owner
        binding.lifecycleOwner = this


        // Hide SearchView by default
        binding.wordSearchView.visibility = View.GONE


        // RecyclerView
        binding.recentSearchesList.adapter = WordListAdapter(OnWordClickListener { word, view ->
            findNavController().navigate(DictionaryFragmentDirections.actionNavigationDictionaryToWordFragment(word))
        })


        // RecyclerView empty check
        val adapter = binding.recentSearchesList.adapter as WordListAdapter
        if (adapter.itemCount > 0) {
            binding.noSearchLayout.visibility = View.GONE
        } else {
            binding.noSearchLayout.visibility = View.VISIBLE
        }


        // NAVIGATION
        binding.viewAllWords.setOnClickListener {
            findNavController().navigate(DictionaryFragmentDirections.actionNavigationDictionaryToAllWordsFragment())
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)


        val searchMenu = menu.findItem(R.id.search)
        searchMenu.setOnMenuItemClickListener {menuItem ->
            updateUI(menuItem)
            false
        }
    }

    private fun updateUI(menuItem: MenuItem) {
        menuItem.expandActionView()
        if (binding.wordSearchView.visibility == View.GONE) {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_down)
            menuItem.icon = requireContext().getDrawable(R.drawable.close_primary)
            binding.wordSearchView.visibility = View.VISIBLE
            binding.wordSearchView.startAnimation(animation)
            binding.wordSearchView.requestFocus()
        } else {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_up)
            binding.wordSearchView.visibility = View.GONE
            menuItem.icon = requireContext().getDrawable(R.drawable.ic_search_primary)
            binding.wordSearchView.startAnimation(animation)
        }
    }

    override fun onResume() {
        super.onResume()
        // REVEAL APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.VISIBLE

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}
