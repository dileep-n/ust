package com.diledroid.ustapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.diledroid.ustapplication.R
import com.diledroid.ustapplication.databinding.ActivityHomeBinding
import com.diledroid.ustapplication.login.LoginActivity
import com.diledroid.ustapplication.util.ConnectivityStatus
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val connectivity = ConnectivityStatus(this)
        connectivity.observe(this) { isConnected ->
            if (!isConnected) {
                signOut()
            }
        }
        // Here navigation graph will load the Home fragment page by default
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController: NavController = navHostFragment.navController

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.action_logout) {
//            signOut()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}