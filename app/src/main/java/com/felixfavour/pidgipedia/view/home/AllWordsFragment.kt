package com.felixfavour.pidgipedia.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentAllWordsBinding
import com.felixfavour.pidgipedia.view.OnWordClickListener
import com.felixfavour.pidgipedia.view.dictionary.WordListAdapter
import com.felixfavour.pidgipedia.viewmodel.AllWordsViewModel

/**
 * A simple [Fragment] subclass.
 */
class AllWordsFragment : Fragment() {
    private lateinit var binding: FragmentAllWordsBinding
    private lateinit var viewModel: AllWordsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_words, container, false)
        viewModel = ViewModelProvider(this).get(AllWordsViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.viewModel = viewModel


        binding.allWordsList.adapter = WordListAdapter(OnWordClickListener {word, view ->
            findNavController().navigate(AllWordsFragmentDirections.actionAllWordsFragmentToWordFragment(word.wordId))
        },
        null
        )

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
