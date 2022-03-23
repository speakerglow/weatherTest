package com.example.weathertest.storage

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun providesStorageProvider(db: MyDataBase): StorageProvider {
        return StorageProviderImpl(db.roomDao())
    }

    @Provides
    @Singleton
    fun providesDB(context: Application): MyDataBase {
        return MyDataBase.build(context)
    }

}