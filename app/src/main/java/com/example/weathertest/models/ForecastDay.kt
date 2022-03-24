package com.example.weathertest.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class ForecastDay(
    val date: String,
    val date_epoch: Long,
    val day: WeatherOnDay
) {
    companion object {
        fun fromJson(json: String): ForecastDay {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(ForecastDay::class.java)
            return adapter.fromJson(json)!!
        }
    }
}


