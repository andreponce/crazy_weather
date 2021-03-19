package com.ponce.cesarschool.myweather.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ponce.cesarschool.myweather.data.DB
import com.ponce.cesarschool.myweather.databinding.ItemCityWeatherBinding
import com.ponce.cesarschool.myweather.databinding.ItemFavoriteBinding
import com.ponce.cesarschool.myweather.model.City
import com.ponce.cesarschool.myweather.model.Country
import com.ponce.cesarschool.myweather.model.Favorite
import com.ponce.cesarschool.myweather.ui.activity.ForecastActivity
import com.ponce.cesarschool.myweather.util.extension.format
import com.ponce.cesarschool.myweather.util.extension.getUnit
import com.ponce.cesarschool.myweather.util.extension.weatherImage
import java.io.File

class FavoriteAdapter(private val onDeleteClick:(Favorite) -> Unit) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(FavoriteDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = getItem(position)
        with(holder) {
            val context = binding.root.context;

            binding.cityNameTxt.text = favorite.cityName
            binding.countryAbbTxt.text = favorite.countryName

            binding.deleteBt.setOnClickListener {
                onDeleteClick(favorite)
            }

            binding.hitAreaBt.setOnClickListener {
                val intent = Intent(context, ForecastActivity::class.java)
                val city = City(favorite.cityId,favorite.cityName,null, null, Country(favorite.countryName), null)
                intent.putExtra("city", city);
                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(public var binding :ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {}

    class FavoriteDiff: DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem == newItem
    }
}