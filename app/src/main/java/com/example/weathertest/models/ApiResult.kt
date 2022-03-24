package com.example.weathertest.models

sealed class ApiResult(val data: Weather?, val message: String?) {

    data class Success(val weather: Weather?) : ApiResult(
        data = weather,
        message = null
    )

    data class Error(val exception: String) : ApiResult(
        data = null,
        message = exception
    )

    class Loading : ApiResult(
        data = null,
        message = null
    )
}
