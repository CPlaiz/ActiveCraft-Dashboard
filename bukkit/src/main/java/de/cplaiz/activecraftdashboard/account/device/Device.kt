package de.cplaiz.activecraftdashboard.account.device

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.account.Account

class Device(val account: Account, var id: String, name: String, var platform: Platform, val token: String) {

    companion object {
        fun of(id: String) = ActiveCraftDashboard.instance.deviceMan.devices.find { it.id == id }
    }

    var active: Boolean = false
    var name = name
        set(value) {
            field = value
            Devices.saveDevice(this)
        }
}

enum class Platform {
    ANDROID,
    IOS,
    WEB,
    DESKTOP_WINDOWS,
    DESKTOP_MAC,
    DESKTOP_LINUX;
}