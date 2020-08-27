package com.felixfavour.pidgipedia.view.dictionary

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentDictionaryBinding
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.viewmodel.DictionaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DictionaryFragment : Fragment() {

    private lateinit var dictionaryViewModel: DictionaryViewModel
    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var suggestionsAdapter: ArrayAdapter<Word>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary, container, false)
        dictionaryViewModel = ViewModelProvider(this).get(DictionaryViewModel::class.java)
        setHasOptionsMenu(true)


        // Bind XML data
        binding.dictionaryViewModel = dictionaryViewModel
        dictionaryViewModel.loadRecentSearches()


        // Set Lifecycle Owner
        binding.lifecycleOwner = this


        // Hide SearchView by default
        binding.wordSearchView.visibility = View.GONE


        // RecyclerView
        binding.recentSearchesList.adapter = WordListAdapter(OnWordClickListener { word, view ->
            findNavController().navigate(DictionaryFragmentDirections.actionNavigationDictionaryToWordFragment(word.wordId))
        })


        //  WORD SEARCH VIEW
        dictionaryViewModel.words.observe(viewLifecycleOwner, Observer { words ->
            suggestionsAdapter = SuggestionsAdapter(
                requireContext(),
                R.layout.suggestions_item,
                words
            )
            binding.wordSearchView.setAdapter(suggestionsAdapter)
            binding.wordSearchView.setOnItemClickListener { adapterView, view, i, l ->
                val word = adapterView.getItemAtPosition(i) as Word
                dictionaryViewModel.addSearchedWord(word)
                dictionaryViewModel.loadRecentSearches()
                findNavController().navigate(DictionaryFragmentDirections.actionNavigationDictionaryToWordFragment(word.wordId))
            }
            binding.wordSearchView.addTextChangedListener {
                val nowLength = it.toString().length
                if (nowLength > 0) {
                    SuggestionsAdapter.suggestions.clear()
                }
            }
        })

        dictionaryViewModel.recentSearches.observe(viewLifecycleOwner, Observer { recentSearches ->
            // RecyclerView empty check
            if (recentSearches.isEmpty()) {
                binding.noSearchLayout.visibility = View.VISIBLE
            } else {
                binding.noSearchLayout.visibility = View.GONE
            }
        })


        // EVENT LISTENERS
        binding.clearAll.setOnClickListener {
            dictionaryViewModel.deleteAllSearches()
            dictionaryViewModel.loadRecentSearches()
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
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val isWordFragNav = DictionaryFragmentArgs.fromBundle(requireArguments()).isWordFragNav
        if (isWordFragNav) {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_down)
            menu.getItem(0).icon = requireContext().getDrawable(R.drawable.close_primary)
            binding.wordSearchView.visibility = View.VISIBLE
            binding.wordSearchView.startAnimation(animation)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            binding.wordSearchView.requestFocus()
        }

        val searchMenu = menu.findItem(R.id.search_dictionary)
        searchMenu.setOnMenuItemClickListener {menuItem ->
            updateUI(menuItem)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
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
