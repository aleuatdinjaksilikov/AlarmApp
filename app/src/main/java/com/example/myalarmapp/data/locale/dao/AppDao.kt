package com.example.myalarmapp.data.locale.dao

import androidx.room.*
import com.example.myalarmapp.data.locale.models.AlarmData

@Dao
interface AppDao {

  @Query("SELECT * FROM my_table")
  suspend fun getAllAlarms():List<AlarmData>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAlarm(alarmData: AlarmData)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateAlarm(alarmData: AlarmData)

  @Delete(entity = AlarmData::class)
  suspend fun deleteAlarm(alarmData: AlarmData)

}