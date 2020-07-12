package com.felixfavour.pidgipedia.view.settings

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentSettingsBinding
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.viewmodel.SettingsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(Pidgipedia.PREFERENCES, Context.MODE_PRIVATE)
        setHasOptionsMenu(true)
        updateUI()


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

        binding.logOut.setOnClickListener {
            settingsViewModel.logUserOut()
            sharedPreferences.edit {
                putBoolean(Pidgipedia.AUTHENTICATION_PREFERENCES, false)
            }
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToAuthenticationActivity()
            )
        }
        
        
        // BUTTON GROUP LISTENERS
        binding.ThemeButtonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            when(checkedId) {
                binding.lightThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    setAppTheme(AppTheme.LIGHT_THEME, requireContext())
                    binding.lightThemeSelection.isChecked = true
                }
                binding.defaultThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    setAppTheme(AppTheme.DEFAULT, requireContext())
                    binding.defaultThemeSelection.isChecked = true
                }
                binding.darkThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    setAppTheme(AppTheme.DARK_THEME, requireContext())
                    binding.darkThemeSelection.isChecked = true
                }
            }
        }


        binding.historyButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                binding.fiftySelection.id -> settingsViewModel.setHistoryCacheLimit(50)
                binding.hundredSelection.id -> settingsViewModel.setHistoryCacheLimit(100)
                binding.oneFiftySelection.id -> settingsViewModel.setHistoryCacheLimit(150)
            }
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
        binding.deleteAllBookmarks.setOnClickListener {
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


    fun updateUI() {
        binding.rankPromotion.setOnClickListener { binding.rankPromotionNotify.performClick() }
        binding.wordOfTheDay.setOnClickListener { binding.wordOfTheDayNotify.performClick()  }
        binding.commentResponses.setOnClickListener { binding.commentResponsesNotify.performClick() }
        binding.wordsApproved.setOnClickListener { binding.wordsApprovedNotify.performClick() }
        binding.miscellaneousNews.setOnClickListener { binding.miscellaneousNewsNotify.performClick() }
    }


    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}
