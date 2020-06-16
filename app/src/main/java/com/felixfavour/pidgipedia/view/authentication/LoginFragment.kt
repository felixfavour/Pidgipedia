package com.felixfavour.pidgipedia.view.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentLoginBinding
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseError.ERROR_USER_NOT_FOUND
import com.google.firebase.FirebaseError.ERROR_WRONG_PASSWORD
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import java.lang.ClassCastException
import java.lang.Exception

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container ,false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)


        // CHECK IF USER IS AUTHENTICATED
        isUserAuthenticated()


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.loginViewModel = loginViewModel


        // NAVIGATION
        binding.createAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }


        // EVENT LISTENERS
        binding.login.setOnClickListener {
            loginViewModel.authenticateUserEmail(
                binding.textInputLayoutEmail.editText?.text.toString(),
                binding.textInputLayoutPassword.editText?.text.toString()
            )
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }


        // OBSERVE LIVEDATA
        loginViewModel.status.observe(viewLifecycleOwner, Observer { status->
            if (status == Connection.SUCCESS) {
                sharedPreferences.edit {
                    putBoolean(Pidgipedia.AUTHENTICATION_PREFERENCES, true)
                }
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                requireActivity().finishAffinity()
            }
        })

        loginViewModel.error.observe(viewLifecycleOwner, Observer { throwable ->

            try {
                val exception = throwable as FirebaseAuthException?
                when(exception?.errorCode) {
                    "ERROR_USER_NOT_FOUND" -> {
                        showWarningDialog(
                            requireContext(),
                            R.string.user_not_found_header,
                            R.string.user_not_found_error
                        )
                    }
                    "ERROR_WRONG_PASSWORD" -> {
                        showWarningDialog(
                            requireContext(),
                            R.string.user_not_found_header,
                            R.string.wrong_password_error
                        )
                    }
                }
            } catch (ex: ClassCastException) {
                // Do nothing
            }
        })


        // GOOGLE AUTHENTICATION
        binding.googleAuth.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(requireContext().getString(R.string.default_web_client_id))
                .build()

            val signInClient = GoogleSignIn.getClient(requireContext(), gso)

            startActivityForResult(signInClient.signInIntent, Pidgipedia.RC_SIGN_IN)

        }


        // TOGGLE LANGUAGE
        binding.language.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.language,
            R.layout.language_spinner_item
        )

        binding.language.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position) {
                    0 -> toast(requireContext(), "english")
                    1 -> toast(requireContext(), "english")
                    2 -> toast(requireContext(), "pidgin")
                }
            }

        }


        return binding.root
    }

    private fun isUserAuthenticated() {
        if(sharedPreferences.getBoolean(Pidgipedia.AUTHENTICATION_PREFERENCES, false)) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
            requireActivity().finishAffinity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Pidgipedia.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener {googleSignInAccount ->
                    loginViewModel.authenticateUserGoogle(googleSignInAccount)
                }
        }
    }

}
