package com.example.weathertest.storage

import androidx.room.*
import com.example.weathertest.models.dao.CheckListDaoEntity
import com.example.weathertest.models.dao.CityDaoEntity

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCheckList(checkList: CheckListDaoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityDaoEntity>)

    @Query("SELECT * FROM cities WHERE name LIKE :name")
    suspend fun getCitiesByName(name: String): List<CityDaoEntity>

    @Query("SELECT * FROM cities WHERE isCurrent")
    suspend fun getCityByCurrentStatus(): CityDaoEntity?

    @Transaction
    suspend fun setNewCurrentCity(newCityDaoEntity: CityDaoEntity) {
        insertCities(listOf(CityDaoEntity(getCityByCurrentStatus()!!.name)))
        insertCities(listOf(newCityDaoEntity))
    }

}