package com.example.finalweatherapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1)

abstract class WeatherDB : RoomDatabase() {
    abstract fun WeatherDAO() : WeatherDAO
    companion object{
        @Volatile
        private var INSTANCE: WeatherDB? = null
        fun getDatabase(context: Context) : WeatherDB{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, WeatherDB::class.java, "weatherDatabase").build()
                }
            }
            return INSTANCE!!
        }

    }
}

