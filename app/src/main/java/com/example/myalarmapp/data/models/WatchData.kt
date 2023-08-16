package com.example.myalarmapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_countries_table")
data class WatchData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val countryName:String,
    val differenceBetweenCountries : String
)