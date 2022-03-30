package com.example.weathertest.storage

import android.util.Log
import com.example.weathertest.models.dao.CityDaoEntity
import kotlin.random.Random

class StorageProviderImpl(private val roomDao: RoomDao) : StorageProvider {
    override suspend fun getSome(): Int {
        return Random.nextInt()
    }

    /*override suspend fun putSome(string: String) {
        roomDao.setCheckList(CheckListDaoEntity(0, string))
    }*/

    override suspend fun putCities(cities: List<CityDaoEntity>) {
        roomDao.insertCities(cities)
    }

    override suspend fun getCities(query: String): List<CityDaoEntity> {
        return roomDao.getCitiesByName(query)
    }

    override suspend fun getCurrentCity(): CityDaoEntity? {
        Log.e("Tag", "storage get current city")
        return roomDao.getCityByCurrentStatus()
    }

    override suspend fun setNewCurrentCity(newCity: CityDaoEntity) {
        roomDao.setNewCurrentCity(newCity)
    }
}