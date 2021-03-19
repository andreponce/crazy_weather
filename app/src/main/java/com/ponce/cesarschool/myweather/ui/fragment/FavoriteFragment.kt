package com.ponce.cesarschool.myweather.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ponce.cesarschool.myweather.adapter.FavoriteAdapter
import com.ponce.cesarschool.myweather.adapter.ForecastAdapter
import com.ponce.cesarschool.myweather.adapter.SearchAdapter
import com.ponce.cesarschool.myweather.data.DB
import com.ponce.cesarschool.myweather.databinding.FragmentFavoriteBinding
import com.ponce.cesarschool.myweather.model.Favorite
import com.ponce.cesarschool.myweather.util.extension.hideKeyboard

class FavoriteFragment : Fragment() {

    private val dao by lazy{ DB.getInstance(requireContext()).getFavoriteDao() }
    private lateinit var binding :FragmentFavoriteBinding
    private lateinit var list : MutableList<Favorite>
    private val favoriteAdapter by lazy {
        FavoriteAdapter { favorite ->
            dao.delete(favorite)
            list.remove(favorite)
            updateList()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi(){
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }

        binding.searchBt.setOnClickListener {
            findCities()
        }
    }

    private fun updateList(){
        favoriteAdapter.submitList(list.toList())
        binding.notFoundTxt.visibility = if(list.size>0) View.GONE else View.VISIBLE
    }

    private fun findCities() {
        binding.searchTxt.hideKeyboard()
        val cityName = binding.searchTxt.text.toString();
        list = dao.getByName(cityName).toMutableList()
        updateList();
    }

    override fun onResume() {
        super.onResume()
        list = dao.getAll().toMutableList()
        updateList();
    }
}