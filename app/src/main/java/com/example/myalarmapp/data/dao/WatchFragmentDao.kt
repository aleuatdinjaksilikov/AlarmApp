package com.example.myalarmapp.data.dao

import androidx.room.*
import com.example.myalarmapp.data.models.AlarmData
import com.example.myalarmapp.data.models.SearchItemData
import com.example.myalarmapp.data.models.WatchData

@Dao
interface WatchFragmentDao {

    @Query("SELECT * FROM watch_countries_table")
    suspend fun getAllWatch(): List<WatchData>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWatch(watchData: WatchData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatch(watchData: WatchData)

    @Query("SELECT * FROM watch_countries_table WHERE countryName LIKE '%' || :name || '%'")
    suspend fun searchCountryByName(name: String):List<SearchItemData>

    @Query("SELECT * FROM watch_countries_table WHERE countryName = :name LIMIT 1")
    suspend fun getWatchByName(name:String):WatchData

    @Delete(entity = WatchData::class)
    suspend fun deleteWatch(watchData: WatchData)
}