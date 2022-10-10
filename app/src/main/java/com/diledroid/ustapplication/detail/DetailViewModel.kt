package com.diledroid.ustapplication.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diledroid.ustapplication.util.NetworkUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener

class DetailViewModel: ViewModel() {
    var ip = MutableLiveData<String>()
    var ipFullDetails = MutableLiveData<String>()
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val rss = NetworkUtility.request("no")
            withContext(Dispatchers.Main) {
                // call to UI thread
                val jsonObject = JSONTokener(rss).nextValue() as JSONObject
                val id = jsonObject.getString("ip")
                ip.postValue(id)

            }
        }
    }
    fun getDetails(ip:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val rss = NetworkUtility.request(ip)
            withContext(Dispatchers.Main) {
                // call to UI thread
                ipFullDetails.postValue(rss.toString())



            }
        }
    }
}