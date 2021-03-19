package com.ponce.cesarschool.myweather.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import androidx.recyclerview.widget.RecyclerView
import com.ponce.cesarschool.myweather.R
import java.util.*

class SettingsFragment :PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

//        PreferenceManager.setDefaultValues(context, R.xml.settings, false);
        setPreferencesFromResource(R.xml.settings, rootKey)

        val currentAppLocale = Locale.getDefault().getLanguage()
        Log.d("PREF", "currentAppLocale: "+currentAppLocale);


        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val systemLang = sharedPreference.getString("lang", "");
        val appLang = sharedPreference.getString("api_lang", "");
        val offline_mode = sharedPreference.getBoolean("offline_mode", false);
        Log.d("PREF", "systemLang: "+systemLang)
        Log.d("PREF", "appLang: "+appLang)
        Log.d("PREF", "offline_mode: "+offline_mode)

        val langPref = findPreference<ListPreference>("api_lang")
        langPref!!.setOnPreferenceChangeListener { preference, newValue ->
//            sharedPreference.edit().putString("lang", newValue as String?).commit()
            true
        }
    }
}