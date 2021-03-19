package com.ponce.cesarschool.myweather.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ponce.cesarschool.myweather.R
import com.ponce.cesarschool.myweather.databinding.ActivityMainBinding
import com.ponce.cesarschool.myweather.ui.fragment.FavoriteFragment
import com.ponce.cesarschool.myweather.ui.fragment.SearchFragment
import com.ponce.cesarschool.myweather.ui.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    private val searchFragment :SearchFragment = SearchFragment()
    private val favoriteFragment :FavoriteFragment = FavoriteFragment()
    private val settingsFragment :SettingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI(){
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_search -> setFragment(searchFragment)
                R.id.menu_favorite -> setFragment(favoriteFragment)
                R.id.menu_settings -> setFragment(settingsFragment)
            }
            true
        }
        binding.bottomNavigationView.selectedItemId =
            R.id.menu_search
    }

    private fun setFragment(fragment :Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(binding.containerView.id, fragment)
                .addToBackStack(null)
                .commit();
    }
}