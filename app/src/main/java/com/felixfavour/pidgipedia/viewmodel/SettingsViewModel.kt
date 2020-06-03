package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.Language
import com.felixfavour.pidgipedia.util.MockData
import com.felixfavour.pidgipedia.util.Pidgipedia

class SettingsViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user.apply {
            loadUser()
        }

    private val _language = MutableLiveData<String>()
    val language: LiveData<String>
        get() = _language.apply {
            loadLanguage()
        }


    private fun loadUser() {
        _user.value = MockData.user1
    }

    private fun loadLanguage() {
        _language.value = Language.ENGLISH
    }


    /*
    * MEthod to add Authentication to the app if the user1 has not been authenticated*/
    private fun addAccount() {
        TODO("Not yet implemented")
    }


    /*
    * Method to Delete all Saved Words by the User*/
    private fun deleteAllBookmarks() {
        TODO("Not yet implemented")
    }


    /*
    * Method to Delete all user1's search histories*/
    private fun deleteSearchHistories() {
        TODO("Not yet implemented")
    }


    /*Method to delete all records of the user1 from remote and local Database*/
    private fun deleteAccount() {
        TODO("Not yet implemented")
    }

}
