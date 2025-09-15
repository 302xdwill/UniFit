package com.example.unifit.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDay(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        return sdf.format(date)
    }
}
