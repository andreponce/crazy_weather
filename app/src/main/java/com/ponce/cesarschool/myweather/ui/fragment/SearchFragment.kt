package com.ponce.cesarschool.myweather.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.ponce.cesarschool.myweather.R
import com.ponce.cesarschool.myweather.adapter.SearchAdapter
import com.ponce.cesarschool.myweather.databinding.FragmentSearchBinding
import com.ponce.cesarschool.myweather.model.FindResult
import com.ponce.cesarschool.myweather.service.RetrofitManager
import com.ponce.cesarschool.myweather.util.extension.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class SearchFragment : Fragment() {

    companion object {
        private const val TAG = "SearchFragment"
    }

    private lateinit var binding :FragmentSearchBinding
    private val searchAdapter by lazy { SearchAdapter() }

    private lateinit var cityName :String
    private lateinit var unit :String
    private lateinit var lang :String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAPI()
        initUi()
    }

    private fun setupAPI(){
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        val file = File(requireContext().filesDir, "api")
        if (!file.exists()) {
            val encryptedFile = EncryptedFile.Builder(
                file,
                requireContext(),
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
            encryptedFile.openFileOutput().use { writer ->
                writer.write("c247dc8b8aaee1f5c2d1543f687b2f3b".toByteArray())
            }
        }
    }

    private fun initUi() {
        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }

        binding.searchBt.setOnClickListener {
            findCities()
        }
    }

    private fun findCities(reload :Boolean = false){
        if(requireContext().isInternetAvailable()){
            binding.searchTxt.hideKeyboard()

            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)

            cityName = if(reload) cityName else binding.searchTxt.text.toString()
            unit = sharedPreference.getUnit(requireContext())
            lang = sharedPreference.getLang(requireContext())

            val call = RetrofitManager.getOpenWeatherService().findCity(
                cityName,
                unit,
                lang,
                requireContext().getToken()
            )

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setView(R.layout.dialog_loading)
            val dialog: AlertDialog = builder.create()
            dialog.setOnCancelListener {
                call.cancel()
            }
            dialog.show();

            call.enqueue(object : Callback<FindResult> {
                override fun onResponse(call: Call<FindResult>, response: Response<FindResult>) {
                    if (response.isSuccessful) {
                        searchAdapter.submitList(response.body()?.cities)
                        binding.notFoundTxt.visibility = if(response.body()?.cities!!.size==0) View.VISIBLE else View.GONE
                    } else {
                        Log.w(TAG, "onResponse: ${response.message()}")
                        Toast.makeText(requireContext(), getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }

                override fun onFailure(call: Call<FindResult>, t: Throwable) {
                    Log.e(TAG, "onFailure", t)
                    dialog.dismiss()
                }
            })
        }else {
            Toast.makeText(requireContext(), getString(R.string.no_network_access), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if(::cityName.isInitialized){
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
            val currentUnit = sharedPreference.getUnit(requireContext())
            val currentLang = sharedPreference.getLang(requireContext())
            if(unit!=currentUnit || lang!=currentLang) findCities(true);
        }
    }
}