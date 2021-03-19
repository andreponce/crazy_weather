package com.ponce.cesarschool.myweather.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ponce.cesarschool.myweather.R
import com.ponce.cesarschool.myweather.databinding.ItemCityWeatherBinding
import com.ponce.cesarschool.myweather.model.City
import com.ponce.cesarschool.myweather.model.Forecast
import com.ponce.cesarschool.myweather.ui.activity.ForecastActivity
import com.ponce.cesarschool.myweather.util.extension.format
import com.ponce.cesarschool.myweather.util.extension.getUnit
import com.ponce.cesarschool.myweather.util.extension.weatherImage
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ViewHolder>(ForecastDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = getItem(position)
        with(holder){
            val context = binding.root.context;

            binding.cityNameTxt.text = java.text.SimpleDateFormat("MMM dd, yyyy h:mm a")
                .format( Date(forecast.dt*1000));
            binding.countryAbbTxt.visibility = android.view.View.GONE
            binding.skyStateTxt.text = forecast.weathers[0].description
            binding.temperatureTxt.text = forecast.main.temp.format()
            binding.unitTxt.text = context.getUnit()
            binding.humidityTxt.text = forecast.main.humidity.format()
            binding.windTxt.text = forecast.wind.speed.format()
            binding.skyImg.load(forecast.weathers[0].icon.weatherImage()) {
                crossfade(true)
            }
        }
    }

    inner class ViewHolder(public val binding: ItemCityWeatherBinding): RecyclerView.ViewHolder(binding.root) {}

    class ForecastDiff: DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem
    }

}