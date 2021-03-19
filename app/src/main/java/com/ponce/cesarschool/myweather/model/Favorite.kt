package com.ponce.cesarschool.myweather.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites")
data class Favorite(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,

    @ColumnInfo(name = "cityId")
    var cityId: Long,

    @ColumnInfo(name = "cityName")
    var cityName: String,

    @ColumnInfo(name = "countryName")
    var countryName: String
) : Parcelable {
    constructor(cityId: Long, cityName: String, countryName: String) : this(null, cityId, cityName, countryName)
}