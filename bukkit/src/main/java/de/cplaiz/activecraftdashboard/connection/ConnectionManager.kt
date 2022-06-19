package de.cplaiz.activecraftdashboard.connection

import de.cplaiz.activecraftcore.playermanagement.Profile

class ConnectionManager {

    val availableCodes: HashMap<Profile, String> = HashMap()

    fun generateRegistrationCode(profile: Profile) {
        availableCodes[profile] = ""
    }

}