package com.example.weathertest.storage

import com.example.weathertest.models.dao.CityDaoEntity

interface StorageProvider {

    suspend fun getSome(): Int

    suspend fun putSome(string: String)

    suspend fun putCities(cities: List<CityDaoEntity>)

    suspend fun getCities(query: String): List<CityDaoEntity>

    suspend fun getCurrentCity(): CityDaoEntity?

    suspend fun setNewCurrentCity(newCity: CityDaoEntity)

}