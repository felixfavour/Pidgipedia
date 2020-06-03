package com.felixfavour.pidgipedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.util.MockData

class ProfileViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user.apply {
            loadUser()
        }


    private fun loadUser() {
        _user.value = MockData.user2
    }

}
