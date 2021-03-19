package com.ponce.cesarschool.myweather.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ponce.cesarschool.myweather.R
import com.ponce.cesarschool.myweather.adapter.ForecastAdapter
import com.ponce.cesarschool.myweather.data.DB
import com.ponce.cesarschool.myweather.databinding.ActivityForecastBinding
import com.ponce.cesarschool.myweather.model.City
import com.ponce.cesarschool.myweather.model.Favorite
import com.ponce.cesarschool.myweather.model.FindResult
import com.ponce.cesarschool.myweather.model.ForecastResult
import com.ponce.cesarschool.myweather.service.RetrofitManager
import com.ponce.cesarschool.myweather.ui.fragment.SearchFragment
import com.ponce.cesarschool.myweather.util.extension.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ForecastActivity"
    }

    private val forecastAdapter by lazy { ForecastAdapter() }
    private lateinit var binding :ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getParcelableExtra<City>("city")!!;
        val dao = DB.getInstance(this).getFavoriteDao()
        var favorite :Favorite? = dao.getByCityId(city.id);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Forecast";

        updateData(city);
        binding.favoriteBt.text = if(favorite!=null) getString(R.string.delete) else getString(R.string.favorite);

        if(isInternetAvailable()) {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
            val unit = sharedPreference.getUnit(this)
            val lang = sharedPreference.getLang(this)
            val call = RetrofitManager.getOpenWeatherService().findForecast(
                city.id,
                unit,
                lang,
                getToken()
            )

            binding.rvForecasts.apply {
                layoutManager = LinearLayoutManager(this@ForecastActivity)
                adapter = forecastAdapter
            }

            call.enqueue(object : Callback<ForecastResult> {
                override fun onResponse(call: Call<ForecastResult>, response: Response<ForecastResult>) {
                    if (response.isSuccessful) {
                        forecastAdapter.submitList(response.body()?.forecasts)
                    } else {
                        Log.w(TAG, "onResponse: ${response.message()}")
                        Toast.makeText(this@ForecastActivity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ForecastResult>, t: Throwable) {
                    Log.e(TAG, "onFailure", t)
                }
            })
        }else{
            Toast.makeText(this, getString(R.string.no_network_access), Toast.LENGTH_SHORT).show()
        }

        binding.favoriteBt.setOnClickListener {
            if(favorite!=null){
                dao.delete(favorite!!);
                favorite = null;
                binding.favoriteBt.text = getString(R.string.favorite);
                Toast.makeText(this,getString(R.string.removed_from_favorites),Toast.LENGTH_SHORT).show()
            }else{
                favorite = Favorite(city.id, city.name, city.country!!.name)
                dao.insert(favorite!!)
                binding.favoriteBt.text = getString(R.string.delete);
                Toast.makeText(this,getString(R.string.added_to_favorites),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateData(city :City){
        binding.titleTxt.text = "Weather in ${city.name}, ${city.country?.name}"
        binding.temperatureTxt.text = city.main?.temp?.let { it.format() }
        binding.unitTxt.text = city.main?.temp?.let{ getUnit() }
        binding.skyImg.load(city.weathers?.get(0)?.icon?.weatherImage()) {
            crossfade(true)
        }
        if(city.main==null) findCityById(city.id);
    }

    private fun findCityById(id :Long){
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val call = RetrofitManager.getOpenWeatherService().findCityById(
            id,
            sharedPreference.getUnit(this),
            sharedPreference.getLang(this),
            getToken()
        )

        call.enqueue(object : Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                if (response.isSuccessful) {
                    response.body()?.let { updateData(it) }
                } else {
                    Log.w(TAG, "onResponse: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<City>, t: Throwable) {
                Log.e(TAG, "onFailure", t)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }
}