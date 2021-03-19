package com.ponce.cesarschool.myweather.util.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ponce.cesarschool.myweather.R

fun SharedPreferences.getUnit(context :Context) :String{
    return this.getString("unit", context.getString(R.string.imperial))!!
}

fun SharedPreferences.getLang(context :Context) :String{
    return this.getString("api_lang", context.getString(R.string.lang_en_value))!!
}