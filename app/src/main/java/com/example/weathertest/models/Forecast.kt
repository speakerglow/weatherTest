package com.example.weathertest.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val forecastday: Array<ForecastDay>
)
