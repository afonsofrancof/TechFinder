package com.example.techfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techfinder.objects.User
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.setUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun register(
        username: String,
        password: String,
        nome: String,
        morada: String,
        email: String
    ): Boolean {
        var boolean = false
        viewModelScope.launch(Dispatchers.IO) {

            boolean = DbAPI.criarConta(username, password, nome, morada, email)
            if (boolean) setUser(User(username, nome, email, password, morada, null))
        }
        return boolean
    }
}