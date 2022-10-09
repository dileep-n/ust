package com.diledroid.ustapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.diledroid.ustapplication.R
import com.diledroid.ustapplication.login.LoginActivity
import com.diledroid.ustapplication.util.ConnectivityStatus
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val connectivity = ConnectivityStatus(this)
        connectivity.observe(this) { isConnected ->
            if (!isConnected) {
                signOut()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_logout) {
            signOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}