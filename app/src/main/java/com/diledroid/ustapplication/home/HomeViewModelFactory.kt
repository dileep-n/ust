package com.diledroid.ustapplication.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diledroid.ustapplication.data.model.DeviceModel

class HomeViewModelFactory(private val deviceItemsList: ArrayList<DeviceModel>): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(deviceItemsList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}