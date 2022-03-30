package com.example.weathertest.storage

import androidx.room.*
import com.example.weathertest.models.dao.CityDaoEntity

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityDaoEntity>)

    @Query("SELECT * FROM cities WHERE name LIKE '%' || :name || '%'")
    suspend fun getCitiesByName(name: String): List<CityDaoEntity>

    @Query("SELECT * FROM cities WHERE isCurrent")
    suspend fun getCityByCurrentStatus(): CityDaoEntity?

    @Transaction
    suspend fun setNewCurrentCity(newCityDaoEntity: CityDaoEntity) {
        val currentCity = getCityByCurrentStatus()
        if (currentCity != null) insertCities(
            listOf(currentCity.apply { isCurrent = false })
        )
        insertCities(listOf(newCityDaoEntity))
    }

}