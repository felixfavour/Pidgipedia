package com.felixfavour.pidgipedia.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentWordSuggestionBinding
import com.felixfavour.pidgipedia.snack
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.io.InputStream

/**
 * A simple [Fragment] subclass.
 */
class WordSuggestionFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 1
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
    private lateinit var binding: FragmentWordSuggestionBinding
    private var isRecordingPermitted: Boolean? = null
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // AUDIO PERMISSION
        ActivityCompat.requestPermissions( requireActivity(), permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_word_suggestion, container, false)

        // ACTION FOR: When Enter is clicked on keyboard for synonym input
        binding.synonymsInput.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                addChip(textView.text, binding.synonymChipGroup)
                textView.text = ""
            }
            false
        }

        // ACTION FOR: When Enter is clicked on keyboard for sentences input
        binding.sentencesInput.setOnEditorActionListener { textView, i, keyEvent ->
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

        // Record User Pronunciation
        val mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS)
            } else {
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            }
            setOutputFile("${binding.word.text}-audio")
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        }

        binding.recordSound.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (isRecordingPermitted!!) {
                        mediaRecorder.prepare()
                        mediaRecorder.start()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (isRecordingPermitted!!) {
                        mediaRecorder.stop()
                    }
                }
            }
            false
        }

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
            val imageStream = requireContext().contentResolver.openInputStream(data?.data!!)
            updateUI(imageStream)
        }
    }

    private fun updateUI(imageStream: InputStream?) {
        // Set Height of Layout
        binding.addPicture.layoutParams.height = 100
        binding.addPicture.layoutParams.width = 100

        // Set Margins of Layout
        val marginParams = binding.addPicture.layoutParams as ViewGroup.MarginLayoutParams
        marginParams.setMargins(0,0, 8, 8)
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

}
