package com.example.unifit.domain.model

import java.util.Date

data class WaterIntake(
    val id: Long,
    val userId: Long,
    val amountMl: Int,
    val date: Date
)
