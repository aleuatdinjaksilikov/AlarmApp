package com.example.myalarmapp.data.locale.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_table")
data class AlarmData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var timeHour : Int,
    var timeMinute : Int,
    var installed : Boolean,
    var daysOfWeek : Int
        )