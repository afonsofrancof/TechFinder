package com.example.techfinder.activities


import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.techfinder.R
import com.example.techfinder.databinding.ActivityMainBinding
import com.example.techfinder.utils.Extensions.Companion.getUser
import com.example.techfinder.utils.Extensions.Companion.logOut
import com.google.android.gms.location.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    private lateinit var toolbar: MaterialToolbar

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var navigationView: NavigationView

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
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.nav_open, R.string.nav_close
        )
        navController.addOnDestinationChangedListener{ _, destination, arguments ->
            toggle.isDrawerIndicatorEnabled =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (destination.id == R.id.shopsFeedFragment) title = "Tech Finder"
        }
        toggle.toolbarNavigationClickListener = View.OnClickListener{ this.onBackPressed() }
        drawerLayout.addDrawerListener(toggle)


        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.perfil -> {
                navController.navigate(R.id.perfilFragment)
                toolbar.title = getUser()?.username ?: "Perfil"

                drawerLayout.closeDrawers()
            }
            R.id.favoritos -> {
                navController.navigate(R.id.favourites_feed)
                toolbar.title = "Lojas favoritas"
                drawerLayout.closeDrawers()
            }
            R.id.comentarios -> {
                navController.navigate(R.id.comentariosFragment)
                toolbar.title = "Os meus comentários"
                drawerLayout.closeDrawers()
            }
            R.id.alterar_palavra_passe -> {

            }
            R.id.logout -> {
                logOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        return true
    }

}