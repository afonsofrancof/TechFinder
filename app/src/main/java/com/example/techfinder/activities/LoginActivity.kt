package com.example.techfinder.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.techfinder.R
import com.example.techfinder.databinding.ActivityLoginBinding
import com.example.techfinder.databinding.ActivityMainBinding
import com.example.techfinder.utils.Extensions.Companion.getUser
import com.example.techfinder.viewModels.LoginActivityViewModel


class LoginActivity: AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel : LoginActivityViewModel by lazy {
        ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        if(getUser()!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_login) as NavHostFragment
        navController = navHostFragment.navController



    }
}