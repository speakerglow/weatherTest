package com.example.weathertest.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = CityContract.tableName)
data class CityDaoEntity(
    @PrimaryKey
    @ColumnInfo(name = CityContract.Column.name)
    val name: String,
    @ColumnInfo(name = CityContract.Column.isCurrent, defaultValue = "false")
    var isCurrent: Boolean? = null
)