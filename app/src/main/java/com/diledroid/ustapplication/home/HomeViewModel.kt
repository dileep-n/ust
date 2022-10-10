package com.diledroid.ustapplication.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diledroid.ustapplication.data.model.DeviceModel

class HomeViewModel(private val deviceItemsList: ArrayList<DeviceModel>):ViewModel() {

    private val filteredList = ArrayList<DeviceModel>()

    // this live data observed from home fragment
    private val _oldFilteredDevice: MutableLiveData<ArrayList<DeviceModel>> = MutableLiveData()
    val oldFilteredDevice: LiveData<ArrayList<DeviceModel>>
        get() = _oldFilteredDevice


    init {
        _oldFilteredDevice.postValue(deviceItemsList)
    }
}