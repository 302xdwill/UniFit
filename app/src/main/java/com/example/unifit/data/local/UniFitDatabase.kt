package com.example.unifit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.unifit.data.local.dao.UserDao
import com.example.unifit.data.local.dao.HabitDao
import com.example.unifit.data.local.dao.WaterIntakeDao
import com.example.unifit.data.local.entity.UserEntity
import com.example.unifit.data.local.entity.WaterIntakeEntity
import com.example.unifit.data.local.entity.HabitEntity

@Database(
    entities = [UserEntity::class, WaterIntakeEntity::class, HabitEntity::class],
    version = 2, // ⚠️ súbelo si cambiaste algo
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UniFitDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun waterIntakeDao(): WaterIntakeDao
    abstract fun habitDao(): HabitDao
}

