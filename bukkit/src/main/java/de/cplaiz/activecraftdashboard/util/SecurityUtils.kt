package de.cplaiz.activecraftdashboard.util

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

fun String.hash(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

object SecurityUtils {
    fun genToken(length: Int): String {
        val secureRandom = SecureRandom()
        val base64Encoder: Base64.Encoder = Base64.getUrlEncoder()
        val randomBytes = ByteArray(length)
        secureRandom.nextBytes(randomBytes)
        return base64Encoder.encodeToString(randomBytes)
    }
}