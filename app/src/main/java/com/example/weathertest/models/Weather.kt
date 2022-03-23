package com.example.weathertest.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class Weather(
    val location: Location,
    val current: CurrentWeather
){
    companion object{
        fun fromJson(json: String): Weather{
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Weather::class.java)
            return adapter.fromJson(json)!!
        }
    }
}

