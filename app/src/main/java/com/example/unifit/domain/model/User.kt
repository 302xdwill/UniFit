package com.example.unifit.domain.model

data class User(
    val id: Long = 0, // 👈 valor por defecto para registros nuevos
    val username: String,
    val email: String,
    val password: String
)


