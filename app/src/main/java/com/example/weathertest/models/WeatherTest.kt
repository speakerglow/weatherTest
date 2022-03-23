package com.example.weathertest.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class WeatherTest(
    val location: Location,
    val current: CurrentWeather
) {
    companion object {
        fun fromJson(json: String): WeatherTest {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(WeatherTest::class.java)
            return adapter.fromJson(json)!!
        }
    }
}


