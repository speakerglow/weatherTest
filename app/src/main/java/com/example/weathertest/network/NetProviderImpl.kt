package com.example.weathertest.network

import kotlin.random.Random

class NetProviderImpl(private val api: Api) : NetProvider {
    override suspend fun getSome(): String {
//        val builder: Response.Builder = Response.Builder()
//        builder.code(200).message("success").body(ResponseBody.create(null, "null"));
//        return builder.build()
        return Random.nextInt().toString()
    }

    // http://api.weatherapi.com/v1 4b0f5a31a78447dfafb65529222203

    override suspend fun getWeather(): String {
        return api.getCurrentWeather(
            key = "4b0f5a31a78447dfafb65529222203",
            city = "Perm",
            airQuality = "no"
        ).string()
    }

    override suspend fun getForecast(): String {
        return api.getWeatherForecast(
            key = "4b0f5a31a78447dfafb65529222203",
            city = "Perm",
            daysCount = 7,
            airQuality = "no",
            alerts = "no"
        ).string()
    }

//    override suspend fun getWeather(): String {
//        return api.getWeather(
//            latitude = 58.005367,
//            longitude = 56.208407,
//            count = 3,
//            appid = "63cd2d3071ebce1867e6aa6e352ef35d"
//        ).string()
//    }
}