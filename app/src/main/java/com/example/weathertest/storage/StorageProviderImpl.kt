package com.example.weathertest.storage

import kotlin.random.Random

class StorageProviderImpl(private val roomDao: RoomDao) : StorageProvider {
    override suspend fun getSome(): Int {
        return Random.nextInt()
    }

    override suspend fun putSome(string: String) {
        roomDao.setCheckList(CheckListDaoEntity(0, string))
    }
}