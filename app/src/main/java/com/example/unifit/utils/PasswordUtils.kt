package com.example.unifit.utils

import java.security.MessageDigest

object PasswordUtils {
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(input: String, hashed: String): Boolean =
        hashPassword(input) == hashed
}