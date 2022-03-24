package com.example.weathertest.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class WeatherOnDay(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val avghumidity: Double,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int
) {
    companion object {
        fun fromJson(json: String): WeatherOnDay {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(WeatherOnDay::class.java)
            return adapter.fromJson(json)!!
        }
    }
}


