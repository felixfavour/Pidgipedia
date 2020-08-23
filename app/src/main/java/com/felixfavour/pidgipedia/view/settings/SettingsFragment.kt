package com.felixfavour.pidgipedia.view.settings

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentSettingsBinding
import com.felixfavour.pidgipedia.util.*
import com.felixfavour.pidgipedia.viewmodel.SettingsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        updateNotificationsUI()


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


        // PUSH NOTIFICATIONS SUBSCRIPTION
        binding.userUpdates.setOnClickListener {
            if (isNotificationSubscribed(Notification.USERS_UPDATES))
                settingsViewModel.unsubscribeNotification(Notification.USERS_UPDATES)
            else
                settingsViewModel.subscribeNotification(Notification.USERS_UPDATES)
        }

        binding.wordOfTheDay.setOnClickListener {
            if (isNotificationSubscribed(Notification.WOD))
                settingsViewModel.unsubscribeNotification(Notification.WOD)
            else
                settingsViewModel.subscribeNotification(Notification.WOD)
        }

        binding.wordUpdates.setOnClickListener {
            if (isNotificationSubscribed(Notification.WORDS_UPDATES))
                settingsViewModel.unsubscribeNotification(Notification.WORDS_UPDATES)
            else
                settingsViewModel.subscribeNotification(Notification.WORDS_UPDATES)
        }

        binding.comments.setOnClickListener {
            if (isNotificationSubscribed(Notification.COMMENTS))
                settingsViewModel.unsubscribeNotification(Notification.COMMENTS)
            else
                settingsViewModel.subscribeNotification(Notification.COMMENTS)
        }

        binding.miscellaneousNews.setOnClickListener {
            if (isNotificationSubscribed(Notification.MISCELLANEOUS))
                settingsViewModel.unsubscribeNotification(Notification.MISCELLANEOUS)
            else
                settingsViewModel.subscribeNotification(Notification.MISCELLANEOUS)
        }
        
        
        // BUTTON GROUP LISTENERS
        binding.ThemeButtonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            when(checkedId) {
                binding.lightThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    setAppTheme(AppTheme.LIGHT_THEME, requireContext())
                    updatePreferencesUI()
                }
                binding.defaultThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    setAppTheme(AppTheme.DEFAULT, requireContext())
                    updatePreferencesUI()
                }
                binding.darkThemeSelection.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    setAppTheme(AppTheme.DARK_THEME, requireContext())
                    updatePreferencesUI()
                }
            }
        }


        // DIALOGS
        binding.deleteAccount.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_account))
                .setMessage(getString(R.string.delete_acct_message))
                .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->
                    snack(requireView(), getString(R.string.Account_deletion_prompt))
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
                    snack(requireView(), getString(R.string.bookmarks_deletion_prompt))
                }.setNegativeButton(getString(R.string.just_kidding), null).show()
        }
        updatePreferencesUI()


        // LISTEN TO SUBSCRIPTION LIVEDATA
        settingsViewModel.isSubscribed.observe(viewLifecycleOwner, Observer { notif ->
            when (notif) {
                Notification.WOD -> binding.wordOfTheDayNotify.isChecked = true
                Notification.COMMENTS -> binding.commentsNotify.isChecked = true
                Notification.WORDS_UPDATES -> binding.wordsUpdatesNotify.isChecked = true
                Notification.USERS_UPDATES -> binding.userUpdatesNotify.isChecked = true
                Notification.MISCELLANEOUS -> binding.miscellaneousNewsNotify.isChecked = true
            }
        })

        settingsViewModel.isUnubscribed.observe(viewLifecycleOwner, Observer { notif ->
            when (notif) {
                Notification.WOD -> binding.wordOfTheDayNotify.isChecked = false
                Notification.COMMENTS -> binding.commentsNotify.isChecked = false
                Notification.WORDS_UPDATES -> binding.wordsUpdatesNotify.isChecked = false
                Notification.USERS_UPDATES -> binding.userUpdatesNotify.isChecked = false
                Notification.MISCELLANEOUS -> binding.miscellaneousNewsNotify.isChecked = false
            }
        })

        return binding.root
    }


    private fun updatePreferencesUI() {
        when (sharedPreferences.getString(THEME_PREFERENCES, AppTheme.LIGHT_THEME)) {
            AppTheme.LIGHT_THEME -> {
                binding.lightThemeSelection.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondaryGreyColor))
            }
            AppTheme.DARK_THEME -> {
                binding.darkThemeSelection.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondaryGreyColor))
            }
            AppTheme.DEFAULT -> {
                binding.defaultThemeSelection.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondaryGreyColor))
            }
        }
    }


    private fun updateNotificationsUI() {
        // FOR USER UPDATES CHECK BUTTON
        if (isNotificationSubscribed(Notification.USERS_UPDATES))
            binding.userUpdatesNotify.isChecked = true

        // FOR WOD CHECK BUTTON
        if (isNotificationSubscribed(Notification.WOD))
            binding.wordOfTheDayNotify.isChecked = true

        // FOR WORDS UPDATES CHECK BUTTON
        if (isNotificationSubscribed(Notification.WORDS_UPDATES))
            binding.wordsUpdatesNotify.isChecked = true

        // FOR COMMENTS CHECK BUTTON
        if (isNotificationSubscribed(Notification.COMMENTS))
            binding.commentsNotify.isChecked = true

        // FOR MISCELLANEOUS CHECK BUTTON
        if (isNotificationSubscribed(Notification.MISCELLANEOUS))
            binding.miscellaneousNewsNotify.isChecked = true
    }

    private fun isNotificationSubscribed(notif: String): Boolean {
        return sharedPreferences.getBoolean(notif, false)
    }


    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


}
