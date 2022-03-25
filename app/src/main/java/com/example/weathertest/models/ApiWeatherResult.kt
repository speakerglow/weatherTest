package com.example.weathertest.models

sealed class ApiWeatherResult(val data: Weather?, val message: String?) {

    data class Success(val weather: Weather?) : ApiWeatherResult(
        data = weather,
        message = null
    )

    data class Error(val exception: String) : ApiWeatherResult(
        data = null,
        message = exception
    )

    class Loading : ApiWeatherResult(
        data = null,
        message = null
    )
}
