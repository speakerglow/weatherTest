package com.example.weathertest.models.dao

object CityContract{
    const val tableName = "cities"
    object Column {
        const val name = "name"
        const val isCurrent = "isCurrent"
    }
}