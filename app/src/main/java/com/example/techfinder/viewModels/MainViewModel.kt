package com.example.techfinder.viewModels


import User
import androidx.lifecycle.ViewModel
import com.example.techfinder.utils.DbAPI
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.ObjectInputStream
import java.lang.System.out
import java.net.Socket

class MainViewModel: ViewModel() {


    fun getUser(username: String) : User? {
        var a = DbAPI();
        var b = a.getUser(username);
        return b
    }
}