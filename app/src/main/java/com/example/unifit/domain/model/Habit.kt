package com.example.unifit.domain.model

import java.util.Date

data class Habit(
    val id: Long = 0,
    val userId: Long,
    val name: String,
    val description: String,
    val dayOfWeek: Int,
    val time: Date,   // ⬅️ Long (System.currentTimeMillis())
    val done: Boolean
)
