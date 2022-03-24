package com.example.weathertest.network

import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

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

    @POST
    suspend fun getCityList(
        @Url url: String = "https://countriesnow.space/api/v0.1/countries/cities",
        @Body body: RequestBody
    ): ResponseBody

}