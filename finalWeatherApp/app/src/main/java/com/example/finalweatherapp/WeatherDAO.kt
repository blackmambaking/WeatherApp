package com.example.finalweatherapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDAO {
    @Insert
    suspend fun insert(weather: Weather)

    @Update
    suspend fun update(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)

    @Query("SELECT * FROM weatherData")
    suspend fun getWeather(): List<Weather>

}