package com.ponce.cesarschool.myweather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ponce.cesarschool.myweather.data.dao.FavoriteDao
import com.ponce.cesarschool.myweather.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class DB: RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    companion object {
        private var instance: DB? = null

        fun getInstance(context: Context): DB {
            if (instance == null) {
                synchronized(DB::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DB::class.java,
                        "weather.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return instance!!
        }
    }
}