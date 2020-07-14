package com.felixfavour.pidgipedia.view.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.setMargins
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentWordSuggestionBinding
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.util.Connection.LOADING
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.showSuccessDialog
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.viewmodel.WordSuggestionViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.InputStream

/**
 * A simple [Fragment] subclass.
 */
class WordSuggestionFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    companion object {
        const val IMAGE_REQUEST_CODE = 1
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    private lateinit var binding: FragmentWordSuggestionBinding
    private lateinit var wordSuggestionViewModel: WordSuggestionViewModel
    private var isRecordingPermitted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var imageUri: Uri? = Uri.EMPTY
    private var file: File? = null

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // AUDIO PERMISSION
        ActivityCompat.requestPermissions( requireActivity(), permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_suggestion, container, false)
        wordSuggestionViewModel = ViewModelProvider(this).get(WordSuggestionViewModel::class.java)


        // ACTION FOR: When Enter is clicked on keyboard for synonym input
        binding.synonymsInput.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                addChip(textView.text, binding.synonymChipGroup)
                textView.text = ""
            }
            false
        }


        // ACTION FOR: When Enter is clicked on keyboard for sentences input
        binding.sentencesInput.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                addChip(textView.text, binding.sentencesChipGroup)
                textView.text = ""
            }
            false
        }

        // Part of Speech Adapter
        ArrayAdapter.createFromResource(requireContext(),
            R.array.part_of_speech_list,
            R.layout.spinner_item
        ).apply {
            binding.partOfSpeech.adapter = this
            this.setDropDownViewResource(R.layout.spinner_item)
        }


        // Word Transcription Formatter
        binding.wordTranscription.setOnFocusChangeListener { view, b ->
            val editText = view as EditText
            if (!editText.isFocused) {
                if (!(editText.text.startsWith("/") && editText.text.endsWith("/"))) {
                    editText.setText("/${editText.text}/")
                }
            }
        }


        // Add Word Image
        binding.addPicture.setOnClickListener {
            addWordPicture(requireView())
        }


        // Animated Vector starting and stopping
        val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.animated_recording
        )
        binding.animatedRecording.setImageDrawable(animatedVectorDrawable)
        animatedVectorDrawable?.start()
        animatedVectorDrawable?.registerAnimationCallback(object: Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                super.onAnimationEnd(drawable)
                animatedVectorDrawable.start()
            }
        })


        // Record User Pronunciation
        toggleRecordingInterface("gone")
        binding.recordSound.setOnTouchListener { _, motionEvent ->

            val isPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
                val path = "${requireContext().cacheDir.path}/audio.m4a"

                val mediaRecorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
                    setOutputFile(path)
                }
                mediaRecorder.prepare()

                try {
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            mediaRecorder.start()
                            toggleRecordingInterface("visible")
                        }
                        MotionEvent.ACTION_UP -> {
                            toggleRecordingInterface("gone")
                            mediaRecorder.stop()
                        }
                    }
                } catch (ex: IllegalStateException) {

                    snack(requireView(), "Voice Recording is no longer in Session")
                    val recordedFile = File(requireContext().cacheDir, "-audio.m4a")
                    if (recordedFile.exists()) {
                        file = recordedFile
                        val mediaPlayer = MediaPlayer().apply {
//                            setAudioAttributes(
//                                AudioAttributes.Builder()
//                                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
//                                    .build()
//                            )
//                            setDataSource(file.path)
//                            prepareAsync()
//                            start()
                        }
                    }
                    /**
                     * This exception is thrown if the recorder starts and stop immediately*/
                }
            }
            true
        }


        // Submit Word to Database Servers
        binding.submit.setOnClickListener {

            val syllabicDivision = binding.syllabicDivision.text.toString()

            val suggestedWord = Word(
                name = binding.word.text.toString(),
                meaning = binding.wordMeaning.text.toString(),
                transcription = binding.wordTranscription.text.toString(),
                etymology = binding.etymology.text.toString(),
                syllabicDivision = syllabicDivision,

                syllables = syllabicDivision.let {
                    var count = 1L
                    it.forEach { char ->
                        if (char == '-') count++
                    }
                    return@let count
                },
                dateCreated = System.currentTimeMillis(),
                englishEquivalent = binding.englishEquivalent.text.toString(),
                imageReference = "",

                partOfSpeech = binding.partOfSpeech.selectedItem.toString(),
                approved = false,
                lastUpdated = System.currentTimeMillis(),

                plural = binding.partOfSpeech.let {spinner ->
                    var pluralForm = binding.word.text.toString()
                    if (spinner.selectedItem.toString() == "Noun") pluralForm += "s"
                    return@let pluralForm
                },
                pronunciationReference = "",

                sentences = binding.sentencesChipGroup.let {
                    val sentences = mutableListOf<String>()
                    binding.sentencesChipGroup.forEach {chip ->
                        chip as Chip
                        sentences.add(chip.text.toString())
                    }
                    return@let sentences
                },

                synonyms = binding.synonymChipGroup.let {
                    val synonyms = mutableListOf<String>()
                    binding.synonymChipGroup.forEach { chip ->
                        chip as Chip
                        synonyms.add(chip.text.toString())
                    }
                    return@let synonyms
                },
                authorId = firebaseAuth.uid!!,
                rejected = false
            )

            if (file != null) {
                wordSuggestionViewModel.uploadSuggestedWord(suggestedWord, Uri.fromFile(file), imageUri)
            } else {
                wordSuggestionViewModel.uploadSuggestedWord(suggestedWord, null, imageUri)
            }
        }


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // OBSERVE LIVE DATA
        wordSuggestionViewModel.status.observe(viewLifecycleOwner, Observer { status->
            if (status == SUCCESS) {
                binding.progressBar.animation = null
                binding.progressBar.visibility = View.GONE
                showSuccessDialog(
                    requireContext(),
                    R.string.successful_suggestion_title,
                    R.string.successful_suggestion_details)
            } else if (status == LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        return binding.root
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] = PackageManager.PERMISSION_GRANTED
            isRecordingPermitted = true
        } else {
            requestPermissions(permissions,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            isRecordingPermitted = false
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE) {
            // Collect Image Data into an input stream
            imageUri = data?.data
            val imageStream = requireContext().contentResolver.openInputStream(data?.data!!)

            updateWordImageUI(imageStream)
        }
    }

    private fun updateWordImageUI(imageStream: InputStream?) {
        // Set Height of Layout
        binding.addPicture.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.addPicture.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.addPicture.text = getString(R.string.change)

        // Set Margins of Layout
        val marginParams = binding.addPicture.layoutParams as ViewGroup.MarginLayoutParams
        marginParams.setMargins(8)
        binding.addPicture.requestLayout()

        val image = Drawable.createFromStream(imageStream, getString(R.string.word_image))
        binding.wordImage.setImageDrawable(image)
    }

    private fun addWordPicture(view: View) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(intent,
            IMAGE_REQUEST_CODE
        )
    }

    /*
    * Method to add synonyms to synonym chip group*/
    private fun addChip(text: CharSequence?, chipGroup: ChipGroup) {
        if (text?.length!! > 0) {

            // Chip ready to be inflated.
            val chip = Chip(requireContext()).apply {
                setText(text.toString())
                isCloseIconVisible = true
                closeIcon = requireContext().getDrawable(R.drawable.close_primary)
                setCloseIconTintResource(R.color.primaryWhiteColor)
                setChipStrokeColorResource(R.color.primaryColor)
                setChipBackgroundColorResource(R.color.primaryColor)
            }

            // Action for Close icon click on
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(it)
            }

            /*
            * GOAL: Make sure there is no duplicate synonym
            * Don't add synonym if synonym chip text is already in a chip in chip group */
            if (chipGroup.childCount > 0) {
                if (!isChipDuplicate(text, chipGroup)) {
                    chipGroup.addView(chip)
                }
            }else {
                chipGroup.addView(chip)
            }
        }
    }


    /**
    * Abstracted function to loop through all chips in a Chip group
    * and compare their `text` property similarity.
    * @return true if a duplicate is found
     *@return false if no duplicate is found*/
    private fun isChipDuplicate(text: CharSequence, chipGroup: ChipGroup): Boolean {
        var count = 0
        for (chip in chipGroup.children) {
            val chipCast = chip as Chip
            if (chipCast.text.toString().compareTo(text.toString(), true) == 0) {
                count++
                if (count >= 2)
                    snack(
                        requireView(),
                        getString(
                            R.string.duplicate_synonym_warning,
                            text
                        )
                    )
                return true
            } else {
                if (chipGroup.get(chipGroup.childCount-1) == chipCast)
                    return false
                else
                    snack(
                        requireView(),
                        getString(
                            R.string.duplicate_synonym_warning,
                            text
                        )
                    )
            }
        }
        return false
    }


    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.GONE

        // SHOW HOME ENABLED ICON
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun toggleRecordingInterface(visibility: String) {
        binding.rootLayout.children.forEach { view ->
            if (view != binding.scrollView && view != binding.progressBar) {
                when (visibility) {
                    "gone" -> view.visibility = View.GONE
                    "visible" -> view.visibility = View.VISIBLE
                }
            }
        }
    }

}
