package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind (
    @SerializedName("speed")
    var speed: Float
) : Parcelable