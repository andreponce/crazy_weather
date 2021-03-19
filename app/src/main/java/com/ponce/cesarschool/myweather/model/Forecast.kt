package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("dt")
    var dt: Long,

    @SerializedName("main")
    var main: Main,

    @SerializedName("wind")
    var wind: Wind,

    @SerializedName("weather")
    var weathers: List<Weather>
) : Parcelable