package de.cplaiz.activecraftdashboard.util

import java.util.*

fun createHexCode(): String = String.format("%06x", Random().nextInt(0xffffff + 1))

fun genRandString(allowedChars: String, length: Int): String {
    val rand = Random()
    val res = StringBuilder()
    for (i in 0 until length) {
        val randIndex = rand.nextInt(allowedChars.length)
        res.append(allowedChars[randIndex])
    }
    return res.toString()
}

fun genRandString(length: Int): String {
    return genRandString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", length)
}
