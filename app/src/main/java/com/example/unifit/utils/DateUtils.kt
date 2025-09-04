package com.example.unifit.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDateTime(date: Date): String =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date)

    fun formatDate(date: Date): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}