package com.ponce.cesarschool.myweather.data.dao

import androidx.room.*
import com.ponce.cesarschool.myweather.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getById(id: Long): Favorite

    @Query("SELECT * FROM favorites WHERE cityId = :cityId")
    fun getByCityId(cityId: Long): Favorite

    @Query("SELECT * FROM favorites WHERE cityName LIKE '%' || :cityName || '%'")
    fun getByName(cityName: String): List<Favorite>

}