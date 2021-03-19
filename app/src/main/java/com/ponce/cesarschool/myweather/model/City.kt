package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("main")
    var main: Main?,

    @SerializedName("wind")
    var wind: Wind?,

    @SerializedName("sys")
    var country: Country?,

    @SerializedName("weather")
    var weathers: List<Weather>?
) : Parcelable