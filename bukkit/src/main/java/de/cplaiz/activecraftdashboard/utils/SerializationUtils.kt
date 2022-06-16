package de.cplaiz.activecraftdashboard.utils

import com.google.gson.Gson

object SerializationUtils {

    fun toJson(o: Any) {
        Gson().toJson(o);
    }

}