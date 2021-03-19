package com.ponce.cesarschool.myweather.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ponce.cesarschool.myweather.R
import com.ponce.cesarschool.myweather.databinding.ItemCityWeatherBinding
import com.ponce.cesarschool.myweather.model.City
import com.ponce.cesarschool.myweather.ui.activity.ForecastActivity
import com.ponce.cesarschool.myweather.util.extension.format
import com.ponce.cesarschool.myweather.util.extension.getUnit
import com.ponce.cesarschool.myweather.util.extension.weatherImage

class SearchAdapter : ListAdapter<City, SearchAdapter.ViewHolder>(SearchDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = getItem(position)
        with(holder){
            val context = binding.root.context;

            binding.cityNameTxt.text = city.name
            binding.countryAbbTxt.text = city.country?.name
            binding.skyStateTxt.text = city.weathers?.get(0)?.description
            binding.temperatureTxt.text = city.main?.temp?.format()
            binding.unitTxt.text = context.getUnit();
            binding.humidityTxt.text = city.main?.humidity?.format()
            binding.windTxt.text = city.wind?.speed?.format()
            binding.skyImg.load(city.weathers?.get(0)?.icon?.weatherImage()) {
                crossfade(true)
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, ForecastActivity::class.java)
                intent.putExtra("city", city);
                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(public val binding: ItemCityWeatherBinding): RecyclerView.ViewHolder(binding.root) {}

    class SearchDiff: DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem == newItem
        override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }

}