package com.example.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val name: String,
    val description: String,
    val dayOfWeek: Int,
    val time: Long,   // ⬅️ usamos Long (millis)
    val done: Boolean
)
