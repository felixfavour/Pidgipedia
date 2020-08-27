package com.felixfavour.pidgipedia.view.home

import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import com.felixfavour.pidgipedia.util.Pidgipedia.AUDIO_REFERENCE
import com.felixfavour.pidgipedia.util.shareWord
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.WordViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordViewModel: WordViewModel
    private val uid = FirebaseAuth.getInstance().uid

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)
        setHasOptionsMenu(true)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.loadUser()
        val openAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_down)
        val closeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_translate_anim_up)
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.supportActionBar


        // SET TOOLBAR TITLE TO EMPTY STRING
        toolbar?.title = ""


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
            val audioFile = File("${requireContext().getExternalFilesDir(null)}/$AUDIO_REFERENCE", "${word?.wordId}.ts")
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
                    audioFile.let {
                        if (it.exists())
                            setDataSource(FileInputStream(audioFile).fd)
                        else
                            setDataSource(audioURL)
                    }
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


        // RECYCLER VIEW SCROLL CHANGES
        binding.wordScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            var offset = 130
            /*
            * offset is the span from the top of the viewport to the wordName view.
            * If a word is certified, the offset is shorter
            * Because the [approve_word* dialog on top of the screen is hidden,
            */
            wordViewModel.word.value?.let {
                if (it.approved || it.certified) {
                    offset = 60
                    if (scrollY > offset) {
                        toolbar?.title = it.name
                    } else if (scrollY <= offset) {
                        toolbar?.title = ""
                    }
                } else {
                    offset = 160
                    if (scrollY > offset) {
                        toolbar?.title = it.name
                    } else if (scrollY <= offset) {
                        toolbar?.title = ""
                    }
                }
            }
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.word_menu, menu)

        wordViewModel.word.observe(viewLifecycleOwner, Observer { word ->
            if (word.certified) {
                menu.removeItem(R.id.edit_word)
                menu.removeItem(R.id.suggest_changes)
            }
            if (word.authorId == uid) {
                menu.removeItem(R.id.suggest_changes)
                menu.removeItem(R.id.see_author)
            }
            if (word.authorId != uid) {
                menu.removeItem(R.id.edit_word)
            }
        })

        menu.forEach { it ->
            it.setOnMenuItemClickListener {menuItem ->
                wordViewModel.word.value?.let {word ->

                    when (menuItem.itemId) {
                        R.id.search -> {
                            findNavController().navigate(WordFragmentDirections.actionWordFragmentToNavigationDictionary())
                        }
                        R.id.edit_word -> {
                            findNavController().navigate(WordFragmentDirections.actionWordFragmentToWordSuggestionFragment(word))
                        }
                        R.id.suggest_changes -> {
                            wordViewModel.latestEventstamp.value?.let {latestEventstamp ->
                                findNavController().navigate(WordFragmentDirections.actionWordFragmentToEventstampFragment(latestEventstamp, true))
                            }
                        }
                        R.id.see_author -> {
                            findNavController().navigate(WordFragmentDirections.actionWordFragmentToProfileFragment2(word.authorId, true))
                        }
                        else -> {
                            // Do nothing
                        }
                    }
                }
                return@setOnMenuItemClickListener true
            }
        }
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
