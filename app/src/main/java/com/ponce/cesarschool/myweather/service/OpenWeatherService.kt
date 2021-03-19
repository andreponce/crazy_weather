package com.ponce.cesarschool.myweather.service

import com.ponce.cesarschool.myweather.model.City
import com.ponce.cesarschool.myweather.model.FindResult
import com.ponce.cesarschool.myweather.model.ForecastResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("/data/2.5/find")
    fun findCity(
        @Query("q")
        cityName: String,

        @Query("units")
        units: String,

        @Query("lang")
        lang: String,

        @Query("appid")
        appId: String
    ): Call<FindResult>

    @GET("/data/2.5/weather")
    fun findCityById(
        @Query("id")
        id: Long,

        @Query("units")
        units: String,

        @Query("lang")
        lang: String,

        @Query("appid")
        appId: String
    ): Call<City>


    @GET("/data/2.5/forecast")
    fun findForecast(
        @Query("id")
        id: Long,

        @Query("units")
        units: String,

        @Query("lang")
        lang: String,

        @Query("appid")
        appId: String
    ): Call<ForecastResult>
}