package com.felixfavour.pidgipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.pidgipedia.databinding.FragmentForgotPasswordBinding
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.util.showSuccessDialog
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.viewmodel.ForgotPasswordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.viewModel = viewModel


        // EVENT LISTENERS

        binding.forgotPasswordToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            onDetach()
        }

        binding.sendLink.setOnClickListener {
            val email = binding.forgotPasswordEmailLayout.editText?.text.toString()
            if (email.isNotEmpty()) {
                viewModel.requestNewPassword(email)
            }
        }


        // OBSERVE LIVEDATA
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status == Connection.SUCCESS) {
                showSuccessDialog(requireContext(), R.string.sent, R.string.request_password_success)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { exception ->
            snack(requireView(), exception.localizedMessage!!)
        })


        return binding.root
    }
}