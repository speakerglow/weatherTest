package com.example.weathertest.models

import com.example.weathertest.models.dao.CityDaoEntity
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class CitiesApiResponse(
    val error: Boolean,
    val msg: String,
    val data: List<String>
) {
    companion object {
        fun fromJson(json: String): CitiesApiResponse {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(CitiesApiResponse::class.java)
            return adapter.fromJson(json)!!
        }
    }

}
