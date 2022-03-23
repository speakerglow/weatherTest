package com.example.weathertest.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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