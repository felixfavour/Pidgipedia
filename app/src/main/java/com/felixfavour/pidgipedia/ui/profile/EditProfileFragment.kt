package com.felixfavour.pidgipedia.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentEditProfileBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

class EditProfileFragment : Fragment() {
    private lateinit var binding : FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_profile, container, false)

        // Activate the Date Picker
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

    private fun formatDate(num: Int) : String {
        if (num < 10)
            return "0$num"
        return num.toString()
    }

}
