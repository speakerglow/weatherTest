package com.example.weathertest.models

data class ForecastDay(
    val date: String,
    val date_epoch: Long,
    //val day: ,
    val astro: String,
    val hour: String
)
