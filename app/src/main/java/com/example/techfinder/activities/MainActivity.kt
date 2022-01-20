package com.example.techfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.ActivityMainBinding
import com.example.techfinder.viewModels.MainViewModel
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import android.view.Menu
import androidx.navigation.ui.AppBarConfiguration
import com.example.techfinder.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var toolbar: Toolbar

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val appBarConfiguration = AppBarConfiguration(navController.graph)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
       menuInflater.inflate(com.example.techfinder.R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}