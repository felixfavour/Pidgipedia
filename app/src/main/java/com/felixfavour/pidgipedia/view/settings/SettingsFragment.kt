package com.felixfavour.pidgipedia.view.settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentSettingsBinding
import com.felixfavour.pidgipedia.viewmodel.SettingsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        setHasOptionsMenu(true)


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // BIND XML DATA
        binding.settingsViewModel = settingsViewModel


        // NAVIGATION
        binding.goToProfile.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileActivity())
        }
        binding.changeLanguage.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLanguageFragment())
        }
        binding.goToInformation.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToInformationFragment())
        }


        // DIALOGS
        binding.deleteAccount.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_account))
                .setMessage(getString(R.string.delete_acct_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->

                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }
        binding.deleteAllHistory.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_all_history))
                .setMessage(getString(R.string.delete_history_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->

                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }
        binding.delteAllBookmarks.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_all_bookmarks))
                .setMessage(getString(R.string.delete_bookmarks_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->

                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.settings_menu, menu)

        menu.findItem(R.id.menu_add_account).setOnMenuItemClickListener {

            false
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
