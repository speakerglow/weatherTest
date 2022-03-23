package com.example.weathertest.storage

interface StorageProvider {

    suspend fun getSome(): Int

    suspend fun putSome(string: String)

}