package com.example.myalarmapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_table")
data class AlarmData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var timeHour : String,
    var timeMinute : String,
    var installed : Boolean,
    var textDaysSelected : String,
    var isMondayActivated : Boolean = false,
    var isTuesdayActivated : Boolean = false,
    var isWednesdayActivated : Boolean = false,
    var isThursdayActivated : Boolean = false,
    var isFridayActivated : Boolean = false,
    var isSaturdayActivated : Boolean = false,
    var isSundayActivated : Boolean = false,
        )