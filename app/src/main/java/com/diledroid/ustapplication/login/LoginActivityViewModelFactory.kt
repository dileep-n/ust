package com.diledroid.ustapplication.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class LoginActivityViewModelFactory(
    private val application: Application,
    private val listener: OnSignInStartedListener
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginActivityViewModel::class.java))
            return LoginActivityViewModel(application, listener) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

interface OnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}