package com.felixfavour.pidgipedia.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login, container ,false)

        //Navigations
        binding.createAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }

        binding.login.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        }

        return binding.root
    }

}
