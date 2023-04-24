package com.example.weathertest.network

import android.util.Log
import com.example.weathertest.Logi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.random.Random

class NetProviderImpl(private val api: Api) : NetProvider {


    override suspend fun login(): Int {
        return try {
            Logi.makeLog(LOGIN2)
            val code = api.login(body = LOGIN2.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())).string()
            Logi.makeLog("code = $code")
            200
        } catch (ex: Exception){
            Logi.makeLog(ex.message ?: "oshibka")
            666
        }
//        return 666
    }

    override suspend fun getCityList(): String {
        val body = REQUEST_BODY
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return api.getCityList(body = body).string()
    }

    override suspend fun getSome(): String {
//        val builder: Response.Builder = Response.Builder()
//        builder.code(200).message("success").body(ResponseBody.create(null, "null"));
//        return builder.build()
        return Random.nextInt().toString()
    }

    // http://api.weatherapi.com/v1 4b0f5a31a78447dfafb65529222203

    override suspend fun getWeather(): String {
        return api.getCurrentWeather(
            key = API_KEY,
            city = "Perm",
            airQuality = "no"
        ).string()
    }

    override suspend fun getForecast(cityName: String): String {
        Log.e("Tag", "get forecast")
        return api.getWeatherForecast(
            key = API_KEY,
            city = cityName,
            daysCount = 7,
            airQuality = "no",
            alerts = "no"
        ).string()
    }

    companion object {
        private const val LOGIN2 = """
            {"userName":"alexey.dikhtyar@vnpz.lukoil.com","password":"Aa12345","rememberMe":true}
        """
        private const val LOGIN = """
            {
                "userName": "dikhtyaraa",
                "password": "Aa12345",
                "rememberMe": "true"
            }
        """
        private const val API_KEY = "4b0f5a31a78447dfafb65529222203"
        private const val REQUEST_BODY = """ 
            {
    "country": "russia"
}
"""
    }

}