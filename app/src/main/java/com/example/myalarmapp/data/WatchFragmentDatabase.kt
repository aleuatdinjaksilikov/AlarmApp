package com.example.myalarmapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myalarmapp.data.dao.WatchFragmentDao
import com.example.myalarmapp.data.models.WatchData

@Database(entities = [WatchData::class], version = 1)
abstract class WatchFragmentDatabase:RoomDatabase() {

    abstract fun getWatchDao():WatchFragmentDao

    companion object{
        const val WATCH_DATABASE_NAME = "watch_db_name"

        fun getInstance(context: Context): WatchFragmentDatabase {
            return Room.databaseBuilder(
                context,
                WatchFragmentDatabase::class.java,
                WATCH_DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}