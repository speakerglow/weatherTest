package com.example.weathertest.network

import okhttp3.Response
import okhttp3.ResponseBody

interface NetProvider {

    suspend fun getCityList(): String

    suspend fun getSome(): String

    suspend fun getWeather(): String

    suspend fun getForecast(cityName: String): String
}