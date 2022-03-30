package com.example.weathertest.models

import android.util.Log
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class Weather(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast?
) {
    companion object {
        fun fromJson(json: String): Weather {
            Log.e("Tag", "weather parse from json")
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Weather::class.java)
            return adapter.fromJson(json)!!
        }
    }
}

