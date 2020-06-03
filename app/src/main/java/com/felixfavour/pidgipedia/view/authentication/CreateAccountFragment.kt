package com.felixfavour.pidgipedia.view.authentication

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentCreateAccountBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_create_account, container, false)

        // Navigations
        // Create Account begins with first section
        goToFirstSection()
        binding.backButton.visibility = View.GONE

        // Forward Navigations
        binding.proceed.setOnClickListener {
            when (true) {
                isFirstSection() -> {
                    goToSecondSection()
                    updateUI()
                }
                isSecondSection() -> {
                    goToThirdSection()
                    updateUI()
                }
                isThirdSection() -> {
                    startWelcomeAnimation()
                    updateUI()
                }
            }
        }

        // Backward Navigations
        binding.backButton.setOnClickListener {
            when (true) {
                isFirstSection() -> {
                    updateUI()
                }
                isSecondSection() -> {
                    goToFirstSection()
                    updateUI()
                }
                isThirdSection() -> {
                    goToSecondSection()
                    updateUI()
                }
            }
        }

        // Activate Date Picker
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            binding.dateOfBirth.setText("${formatDate(day)}-${formatDate(month+1)}-${formatDate(year)}")
        }

        binding.dateOfBirth.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        return binding.root
    }

    /**
     * Method to navigate to the first section of the Create Account fragment
     * Method contains only one visible TextInputLayout, emailTextInputLayout*/
    private fun goToFirstSection() {
        binding.registerQueriesLayout.children.forEach {view ->
            if (view == binding.textInputLayoutEmail) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }


    /**
     * Method to navigate to the second section of the Create Account fragment
     * Method contains five visible TextInputLayout, all except passwordTextInputLayout*/
    private fun goToSecondSection() {
        binding.registerQueriesLayout.children.forEach { view ->
            if (view == binding.textInputLayoutPassword) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Method to navigate to the last section of the Create Account fragment
     * Method contains only one visible TextInputLayout, passwordTextInputLayout*/
    private fun goToThirdSection() {
        binding.registerQueriesLayout.children.forEach { view ->
            if (view == binding.textInputLayoutPassword) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }

    /**
    * METHODS TO CHECK CURRENT SECTIONS IN VIEW
    * The idea behind it is to check the number of visible TextInputLayout views in the root view,
    * Thereafter, check the view that is visible. If the view is the emailTextInputLayout, then it
    * is surely the first Section, if the view is the passwordTextInputLayout, then it is surely
    * the third Section.
    *
    * On the other hand, if there are exactly five visible TextInputLayout views, it is surely the
    * Second Section*/

    private fun isFirstSection() : Boolean {
        var visibilityCount = 0
        var visibleView : View? = null

        binding.registerQueriesLayout.forEach { view ->
            if (view.visibility == View.VISIBLE) {
                visibleView = view
                visibilityCount++
            }
        }
        if (visibilityCount == 1 && visibleView == binding.textInputLayoutEmail)
            return true

        return false
    }

    private fun isSecondSection() : Boolean {
        var visibilityCount = 0
        binding.registerQueriesLayout.forEach { view ->
            if (view.visibility == View.VISIBLE)
                visibilityCount++
        }
        if (visibilityCount == 5)
            return true

        return false
    }

    private fun isThirdSection() : Boolean {
        var visibilityCount = 0
        var visibleView : View? = null

        binding.registerQueriesLayout.forEach { view ->
            if (view.visibility == View.VISIBLE) {
                visibleView = view
                visibilityCount++
            }
        }
        if (visibilityCount == 1 && visibleView == binding.textInputLayoutPassword)
            return true

        return false
    }

    private fun updateUI() {
        when (true) {
            isFirstSection() -> {
                binding.backButton.visibility = View.GONE
                binding.header.text = getString(R.string.enter_email_first)
                binding.proceed.text = getString(R.string.proceed)
            }
            isSecondSection() -> {
                binding.backButton.visibility = View.VISIBLE
                binding.header.text = getString(R.string.few_more_steps)
                binding.proceed.text = getString(R.string.proceed)
            }
            isThirdSection() -> {
                binding.backButton.visibility = View.VISIBLE
                binding.header.text = getString(R.string.choose_a_password)
                binding.proceed.text = getString(R.string.finish)
            }
        }
    }

    private fun startWelcomeAnimation() {
        val welcomeAnim = AnimationUtils.loadAnimation(requireContext(),
            R.anim.welcome_anim
        )
        binding.welcomeNoteLayout.visibility = View.VISIBLE
        binding.welcomeNoteLayout.startAnimation(welcomeAnim)
    }

    private fun formatDate(num: Int) : String {
        if (num < 10)
            return "0$num"
        return num.toString()
    }

}
