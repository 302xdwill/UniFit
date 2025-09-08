package com.example.unifit

import android.app.Application
import androidx.room.Room
import com.example.unifit.data.local.UniFitDatabase
import com.example.unifit.data.repository.UserRepositoryImpl
import com.example.unifit.data.repository.WaterIntakeRepositoryImpl
import com.example.unifit.domain.repository.UserRepository
import com.example.unifit.domain.repository.WaterIntakeRepository
import com.example.unifit.utils.SessionManager

class UniFitApp : Application() {
    lateinit var database: UniFitDatabase
    lateinit var userRepository: UserRepository
    lateinit var waterRepository: WaterIntakeRepository
    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, UniFitDatabase::class.java, "unifit_db")
            .fallbackToDestructiveMigration()
            .build()

        userRepository = UserRepositoryImpl(database.userDao())
        waterRepository = WaterIntakeRepositoryImpl (database.waterIntakeDao())
        sessionManager = SessionManager(applicationContext)
    }
}
