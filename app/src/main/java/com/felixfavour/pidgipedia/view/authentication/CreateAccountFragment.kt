package com.felixfavour.pidgipedia.view.authentication

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentCreateAccountBinding
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.viewmodel.CreateAccountViewModel
import com.google.android.material.textfield.TextInputLayout
import java.lang.IllegalArgumentException
import java.util.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private lateinit var viewModel: CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
        viewModel = ViewModelProvider(this).get(CreateAccountViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.viewModel = viewModel


        // NAVIGATION
        // Create Account begins with first section
        goToFirstSection()


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

                    // CREATE ACCOUNT WITH EMAIL AND PASSWORD
                    try {
                        viewModel.createAccount(
                            binding.email.text.toString(),
                            binding.password.text.toString(),
                            binding.username.text.toString()
                        )
                    } catch (ex: IllegalArgumentException) {
                        snack(requireView(), getString(R.string.fill_all_fields))
                        viewModel.executionFailed()
                    }
                }
            }
        }

        // Backward Navigations
        binding.backButton.setOnClickListener {
            when (true) {
                isFirstSection() -> {
                    updateUI()
                    requireActivity().onBackPressed()
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


        // OBSERVE LIVE DATA
        viewModel.creationStatus.observe(viewLifecycleOwner, Observer {  creationStatus ->
            if (creationStatus == Connection.SUCCESS) {
                findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToMainActivity())
            }
        })


        // VALIDATE FIELDS
        binding.password.addTextChangedListener {
            validateFields(binding.textInputLayoutPassword)
        }
        binding.username.addTextChangedListener {
            validateFields(binding.textInputLayoutUsername)
        }
        binding.email.addTextChangedListener {
            validateFields(binding.textInputLayoutEmail)
        }



        // Activate Date Picker
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener() { _: DatePicker, year: Int, month: Int, day: Int ->
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
            if (view == binding.textInputLayoutEmail || view == binding.textInputLayoutUsername) {
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
        if (visibilityCount == 2)
            return true

        return false
    }


    private fun isSecondSection() : Boolean {
        var visibilityCount = 0
        binding.registerQueriesLayout.forEach { view ->
            if (view.visibility == View.VISIBLE)
                visibilityCount++
        }
        if (visibilityCount == 6)
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
                binding.backButton.visibility = View.VISIBLE
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


    private fun formatDate(num: Int) : String {
        if (num < 10)
            return "0$num"
        return num.toString()
    }


    private fun validateFields(textInputLayout: TextInputLayout): Boolean {
        val editText = textInputLayout.editText!!

        when (textInputLayout) {

            binding.textInputLayoutEmail -> {
                val text = editText.text.toString()
                val regex = Pattern.compile("""[\w]+@[\w.]+""").toRegex()
                if (text.matches(regex)) {
                    viewModel.isEmailTaken(text)
                } else {
                    textInputLayout.error = getString(R.string.invalid_email)
                }
            }

            binding.textInputLayoutUsername -> {
                if (editText.text.isNotBlank() && editText.text.length >= 6) {
                    viewModel.isUsernameTaken(editText.text.toString())
                } else {
                    textInputLayout.error = getString(R.string.username_guidelines)
                }
            }

            binding.textInputLayoutFirstName -> {
                return editText.text.isNotBlank()
            }

            binding.textInputLayoutLastName -> {
                return editText.text.isNotBlank()
            }

            binding.textInputLayoutLocation -> {
                return editText.text.isNotBlank()
            }

            binding.textInputLayoutDob -> {
                return editText.text.isNotBlank()
            }

            binding.textInputLayoutPassword -> {
                val text = editText.text.toString()
                val regex = Pattern.compile("""^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}${'$'}""").toRegex()
                if (text.matches(regex)) {
                    textInputLayout.isErrorEnabled = false
                } else {
                    textInputLayout.error = getString(R.string.password_guidelines)
                }
            }
        }
        return false
    }

}
