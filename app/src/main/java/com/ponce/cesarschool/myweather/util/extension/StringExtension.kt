package com.ponce.cesarschool.myweather.util.extension

fun String.weatherImage() :String{
    return "http://openweathermap.org/img/wn/${this}@4x.png"
}