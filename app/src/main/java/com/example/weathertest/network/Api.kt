package com.example.weathertest.network

import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("api/Groups/GetAll")
    suspend fun getSome(): ResponseBody

    //    @POST("forecast")
//    suspend fun getWeather(
//        @Query("lat")
//        latitude: Double,
//        @Query("lon")
//        longitude: Double,
//        @Query("cnt")
//        count: Int,
//        @Query("appid")
//        appid: String
//    ): ResponseBody
//
    @POST("current.json")
    suspend fun getCurrentWeather(
        @Query("key")
        key: String,
        @Query("q")
        city: String,
        @Query("aqi")
        airQuality: String
    ): ResponseBody

    @POST("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key")
        key: String,
        @Query("q")
        city: String,
        @Query("days")
        daysCount: Int,
        @Query("aqi")
        airQuality: String,
        @Query("alerts")
        alerts: String
    ): ResponseBody

}