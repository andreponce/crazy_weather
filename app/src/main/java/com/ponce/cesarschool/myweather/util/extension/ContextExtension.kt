package com.ponce.cesarschool.myweather.util.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.ponce.cesarschool.myweather.R
import java.io.File

fun Context.isInternetAvailable(): Boolean {
    var result = false

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    } else {
        cm.activeNetworkInfo?.run {
            if (type == ConnectivityManager.TYPE_WIFI) {
                result = true
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                result = true
            }
        }
    }
    return result
}

fun Context.getToken(): String {
    val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    val encryptedFile = EncryptedFile.Builder(
        File(filesDir, "api"),
        this,
        masterKeyAlias,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()
    var result = ""
    encryptedFile.openFileInput().use { inputStream ->
        result = String(inputStream.readBytes(), Charsets.UTF_8)
    }
    return result
}

fun Context.getUnit() :String{
    val defaultUnit = getString(R.string.imperial);
    return if(PreferenceManager.getDefaultSharedPreferences(this).getUnit(this)==defaultUnit) "ºF" else "ºC";
}