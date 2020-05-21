package com.felixfavour.pidgipedia.ui.dictionary

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.MenuItemCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private lateinit var dictionaryViewModel: DictionaryViewModel
    private lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary, container, false)
        setHasOptionsMenu(true)

        // Hide SearchView by default
        binding.wordSearchView.visibility = View.GONE

        // RecyclerView
        binding.recentSearchesList

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
}
