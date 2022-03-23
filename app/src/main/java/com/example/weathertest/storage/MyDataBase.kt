package com.example.weathertest.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathertest.storage.MyDataBase.Companion.DATABASE_VERSION

@Database(
    entities = [CheckListDaoEntity::class],
    version = DATABASE_VERSION
)
abstract class MyDataBase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        lateinit var instance: MyDataBase
            private set
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDataBase"
        fun build(context: Context): MyDataBase {
            val room = Room.databaseBuilder(context, MyDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
            instance = room
            return room
        }
    }

}