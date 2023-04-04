package com.example.myalarmapp.data.locale.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_table")
data class AlarmData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val timeHour : Int,
    val timeMinute : Int,
    val installed : Boolean,
    val daysOfWeek : Int
        )