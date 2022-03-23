package com.example.weathertest.models

sealed class ApiResult (val data: Weather?, val message:String?) {

    data class Success(val _data: Weather?): ApiResult(
        data = _data,
        message = null
    )

    data class Error(val exception: String): ApiResult(
        data = null,
        message = exception
    )

    data class Loading(val isLoading: Boolean): ApiResult(
        data = null,
        message = null
    )
}
