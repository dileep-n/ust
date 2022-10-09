package com.diledroid.ustapplication.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.diledroid.ustapplication.R
import com.diledroid.ustapplication.databinding.ActivityLoginBinding
import com.diledroid.ustapplication.home.HomeActivity
import com.diledroid.ustapplication.util.ConnectivityStatus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var viewModel: LoginActivityViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
         val connectivity = ConnectivityStatus(this)
        connectivity.observe(this) { isConnected ->
            if (!isConnected) {
                FirebaseAuth.getInstance().signOut()
            }
        }
        firebaseAuth.addAuthStateListener(this.authStateListener)
        val application = requireNotNull(this).application
        val factory = LoginActivityViewModelFactory(application, object : OnSignInStartedListener {
            override fun onSignInStarted(client: GoogleSignInClient?) {
                startActivityForResult(client?.signInIntent, RC_SIGN_IN)
               }
        })
        viewModel = ViewModelProvider(this, factory)[LoginActivityViewModel::class.java]



        binding.loginInBtn.setOnClickListener {
            viewModel.signIn()
        }

        viewModel.currentUser.observe(this) {
            it?.let {
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(this.authStateListener)
    }

    private fun firebaseLogin(data:Intent){
        // this task is responsible for getting ACCOUNT SELECTED
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!

            viewModel.firebaseAuthWithGoogle(account.idToken!!)


        } catch (e: ApiException) {
            Toast.makeText(this, R.string.wrong_text, Toast.LENGTH_SHORT).show()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK && data != null) {
            firebaseLogin(data)
        }
    }
}