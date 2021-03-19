package com.ponce.cesarschool.myweather.model

import com.google.gson.annotations.SerializedName

data class ForecastResult(
    @SerializedName("cod")
    var cod: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("list")
    var forecasts: List<Forecast>
)