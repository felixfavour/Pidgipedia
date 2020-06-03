package com.felixfavour.pidgipedia.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentWordBinding
import com.felixfavour.pidgipedia.viewmodel.WordViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordViewModel: WordViewModel

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // ROUND BORDER WORD IMAGE
        Glide.with(requireContext())
            .load(R.drawable.greta)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
            .into(binding.wordImage)


        // BINDING THE RETRIEVED WORD ARGUMENT PASSED BY NAV COMPONENTS
        val wordArg = WordFragmentArgs.fromBundle(requireArguments()).word
        binding.word = wordArg


        // HIDE AND REVEAL VIEWS
        binding.etymologyButton.setOnClickListener {
            updateUI(binding.etymologyButton, binding.etymology)
        }
        binding.synonymsButton.setOnClickListener {
            updateUI(binding.synonymsButton, binding.synonyms)
        }
        binding.sentencesButton.setOnClickListener {
            updateUI(binding.sentencesButton, binding.sentences)
        }


        // EVENT LISTENERS
        // PLAY MEDIA ON BUTTON CLICKED
        binding.audioButton.setOnClickListener {

        }


        // BOOKMARK WORD ON BUTTON CLICKED
        binding.bookmarkWord.setOnClickListener {

        }


        // SHARE WORD ON BUTTON CLICKED
        binding.shareWord.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT,
                    "${wordArg.name.capitalize(Locale.getDefault())}\n${wordArg.meaning}." +
                            "\n" + getString(R.string.download_app_for_more)
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }


        return binding.root
    }


    private fun toggleButtonIcon(button: ImageButton, icon1: Int, icon2: Int) {

    }

    /*
    * Method to expand and contract views in Word screen*/
    private fun updateUI(button: Button, textView: TextView) {
        if (textView.isVisible) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
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
