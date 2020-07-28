package com.felixfavour.pidgipedia.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentBookmarksBinding
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.view.dictionary.WordListAdapter
import com.felixfavour.pidgipedia.viewmodel.BookmarksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var bookmarksViewModel: BookmarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarksViewModel = ViewModelProvider(this).get(BookmarksViewModel::class.java)
        bookmarksViewModel.loadWords()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.bookmarkViewModel = bookmarksViewModel


        // RECYCLER VIEW
        binding.bookmarksList.adapter = WordListAdapter(OnWordClickListener { word, it ->
            findNavController().navigate(BookmarksFragmentDirections.actionBookmarksFragmentToWordFragment(word.wordId))
        })

        val adapter = binding.bookmarksList.adapter as WordListAdapter


        bookmarksViewModel.words.observe(viewLifecycleOwner, Observer {words ->
            updateUI(words)
        })


        return binding.root
    }


    /*
    * Method to update UI, specifically Views Visibility when recycler view wordList is empty*/
    private fun updateUI(words: List<Word>) {
        if (words.isEmpty()) {
            binding.noBookmarksLayout.visibility = View.VISIBLE
        } else {
            binding.noBookmarksLayout.visibility = View.GONE
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
