package com.example.techfinder.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.viewModels.LoginActivityViewModel


class LoginActivity: AppCompatActivity() {

    private val viewModel : LoginActivityViewModel by lazy {
        ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        if(viewModel.getUser(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



        super.onCreate(savedInstanceState)
    }
}