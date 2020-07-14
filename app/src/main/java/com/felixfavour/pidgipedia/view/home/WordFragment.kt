package com.felixfavour.pidgipedia.view.home

import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentWordBinding
import com.felixfavour.pidgipedia.util.Connection.FAILED
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.shareWord
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.viewmodel.WordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

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
        wordViewModel.loadUser()
        val openAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_down)
        val closeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_up)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // ROUND BORDER WORD IMAGE
        Glide.with(requireContext())
            .load(R.drawable.greta)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
            .into(binding.wordImage)


        // BINDING THE RETRIEVED WORD ARGUMENT PASSED BY NAV COMPONENTS
        val wordId = WordFragmentArgs.fromBundle(requireArguments()).wordId
        wordViewModel.loadWord(wordId)
        binding.wordViewModel = wordViewModel


        // HIDE AND REVEAL VIEWS
        binding.etymologyButton.setOnClickListener {
            updateUI(binding.etymologyButton, binding.etymology)
        }
        binding.synonymsButton.setOnClickListener {
            updateUI(binding.synonymsButton, binding.synonyms)
        }
        binding.sentencesButton.setOnClickListener {
            updateUI(binding.sentencesButton, binding.sentences)
            CoroutineScope(Dispatchers.Default).launch {
                delay(200)
                binding.wordScrollView.fullScroll(View.FOCUS_DOWN)
            }
        }


        // EVENT LISTENERS
        // PLAY MEDIA ON BUTTON CLICKED
        val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.animated_recording
        )
        binding.audioButton.setOnClickListener {
            val word = wordViewModel.word.value
            val audioURL = word?.pronunciationReference
            if (!audioURL.isNullOrEmpty()) {
                val mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()
                    )
                    setDataSource(audioURL)
                    prepareAsync()
                }
                mediaPlayer.setOnPreparedListener {
                    it.start()
                    binding.audioButton.setImageDrawable(animatedVectorDrawable)
                    animatedVectorDrawable?.start()
                    animatedVectorDrawable?.registerAnimationCallback(object: Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            animatedVectorDrawable.start()
                        }
                    })
                    binding.audioButton.isClickable = false
                }
                mediaPlayer.setOnCompletionListener {
                    binding.audioButton.setImageResource(R.drawable.play)
                    binding.audioButton.isClickable = true
                }
            }
        }


        // BOOKMARK WORD ON BUTTON CLICKED
        binding.bookmarkWord.setOnClickListener {
            wordViewModel.toggleBookmarkWord()
            wordViewModel.word.observe(viewLifecycleOwner, Observer { word ->
                if (word.bookmarked) {
                    snack(requireView(), getString(R.string.bookmarks_added))
                } else {
                    snack(requireView(), getString(R.string.bookmarks_removed))
                }
            })
        }


        binding.approveWord.setOnClickListener {
            wordViewModel.approveWord()
        }


        binding.disapproveWord.setOnClickListener {
            wordViewModel.disapproveWord()
        }


        // OBSERVE LIVE DATA CHANGES
        wordViewModel.word.observe(viewLifecycleOwner, Observer { word ->
            binding.shareWord.setOnClickListener {
                shareWord(requireContext(), word)
            }
            if (word.approved || word.rejected) {
                binding.buttonGroup.startAnimation(closeAnimation)
                binding.buttonGroup.visibility = View.GONE
            } else {
                binding.buttonGroup.startAnimation(openAnimation)
                binding.buttonGroup.visibility = View.VISIBLE
            }
        })

        wordViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status == SUCCESS) {
                binding.buttonGroup.startAnimation(closeAnimation)
                binding.buttonGroup.visibility = View.GONE
            } else if (status == FAILED) {
                snack(requireView(), getString(R.string.word_approve_rejection_requirement_warning))
            }
        })


        return binding.root
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
        appLogoContainer?.visibility = View.GONE
    }

}
