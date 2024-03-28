package com.example.finalweatherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherData")
data class Weather (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    val min: String,
    val max: String,
)
