package com.felixfavour.pidgipedia.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentLoginBinding
import com.felixfavour.pidgipedia.util.Connection
import com.felixfavour.pidgipedia.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container ,false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.loginViewModel = loginViewModel


        // NAVIGATION
        binding.createAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }

        binding.login.setOnClickListener {
            loginViewModel.authenticateUserEmail(
                binding.textInputLayoutEmail.editText?.text.toString(),
                binding.textInputLayoutPassword.editText?.text.toString()
            )
        }


        // OBSERVE LIVEDATA
        loginViewModel.loginStatus.observe(viewLifecycleOwner, Observer { status->
            if (status == Connection.SUCCESS) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
            }
        })


        binding.googleAuth.setOnClickListener {
            loginViewModel.authenticateUserGoogle()
        }

        return binding.root
    }

}
