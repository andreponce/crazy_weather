package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main (
    @SerializedName("temp")
    var temp: Float,
    @SerializedName("humidity")
    var humidity: Float
) : Parcelable