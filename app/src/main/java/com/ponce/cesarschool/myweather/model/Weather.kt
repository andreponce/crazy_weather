package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    @SerializedName("main")
    var main: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("icon")
    var icon: String
) : Parcelable