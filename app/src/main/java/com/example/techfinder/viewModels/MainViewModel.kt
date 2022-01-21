package com.example.techfinder.viewModels

import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.User
import com.example.techfinder.utils.DbAPI

class MainViewModel : ViewModel() {

    fun getUser(username: String): User? {
        return DbAPI.getUser(username);
    }
}