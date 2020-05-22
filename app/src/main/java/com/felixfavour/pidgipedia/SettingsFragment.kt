package com.felixfavour.pidgipedia

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.DialogCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.nio.file.Files.delete

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        // NAVIGATIONS
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
                    deleteAccount()
                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }
        binding.deleteAllHistory.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_all_history))
                .setMessage(getString(R.string.delete_history_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->
                    deleteSearchHistories()
                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }
        binding.delteAllBookmarks.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_all_bookmarks))
                .setMessage(getString(R.string.delete_bookmarks_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->
                    deleteAllBookmarks()
                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }


        return binding.root
    }

    /*
    * Method to Delete all Saved Words by the User*/
    private fun deleteAllBookmarks() {
        TODO("Not yet implemented")
    }

    /*
    * Method to Delete all user's search histories*/
    private fun deleteSearchHistories() {
        TODO("Not yet implemented")
    }

    /*Method to delete all records of the user from remote and local Database*/
    private fun deleteAccount() {
        TODO("Not yet implemented")
    }

}
