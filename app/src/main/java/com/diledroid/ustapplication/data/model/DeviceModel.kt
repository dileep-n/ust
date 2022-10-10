package com.diledroid.ustapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceModel(val deviceName:String,val deviceIP:String): Parcelable
