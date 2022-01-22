package com.example.techfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.ActivityMainBinding
import com.example.techfinder.viewModels.MainViewModel
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.TooltipCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.techfinder.R
import com.google.android.material.navigation.NavigationView
import android.view.View

import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem

import androidx.annotation.NonNull
import androidx.core.view.GravityCompat
import com.example.techfinder.fragments.ShopsFeedFragment


class MainActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener{

    private lateinit var navController: NavController

    private lateinit var toolbar : Toolbar

    private lateinit var drawerLayout : DrawerLayout

    private lateinit var navigationView : NavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(this,drawerLayout , toolbar,
        R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.perfil -> {
                navController.navigate(R.id.perfilFragment)
                drawerLayout.closeDrawers();
            }
            R.id.favoritos -> {
                navController.navigate(R.id.favourites_feed)
                drawerLayout.closeDrawers();
            }
            R.id.comentarios -> {
                navController.navigate(R.id.comentariosFragment)
                drawerLayout.closeDrawers();
            }
            R.id.alterar_palavra_passe -> {
                //pop up para alterar pass
            }
            R.id.logout -> {
                //dar logout
            }
        }
        return true
    }

}