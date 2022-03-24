package com.example.weathertest.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathertest.storage.CheckListContract

@Entity(
    tableName = CheckListContract.tableName
)
data class CheckListDaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CheckListContract.Column.id)
    val id: Int,
    @ColumnInfo(name = CheckListContract.Column.checkList)
    val checkList: String
)