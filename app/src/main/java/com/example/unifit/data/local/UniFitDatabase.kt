package com.example.unifit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.unifit.data.local.dao.UserDao
import com.example.unifit.data.local.dao.WaterIntakeDao
import com.example.unifit.data.local.entity.UserEntity
import com.example.unifit.data.local.entity.WaterIntakeEntity

@Database(entities = [UserEntity::class, WaterIntakeEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UniFitDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun waterDao(): WaterIntakeDao
}