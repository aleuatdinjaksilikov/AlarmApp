package com.example.myalarmapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myalarmapp.data.dao.AppDao
import com.example.myalarmapp.data.models.AlarmData


@Database(entities = [AlarmData::class], version = 3)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getDao(): AppDao

    companion object{

        const val DATABASE_NAME = "db_name"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}