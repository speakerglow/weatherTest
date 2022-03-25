package com.example.weathertest.models

import com.example.weathertest.models.dao.CityDaoEntity

sealed class ApiCityResult(val data: List<CityDaoEntity>?, val message: String?) {

    data class Success(val cities: List<CityDaoEntity>?) : ApiCityResult(
        data = cities,
        message = null
    )

    data class Error(val exception: String) : ApiCityResult(
        data = null,
        message = exception
    )

    class Loading : ApiCityResult(
        data = null,
        message = null
    )
}

