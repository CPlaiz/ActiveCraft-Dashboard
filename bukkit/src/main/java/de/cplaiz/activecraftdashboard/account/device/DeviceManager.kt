package de.cplaiz.activecraftdashboard.account.device

import de.cplaiz.activecraftcore.messages.MessageFormatter
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.account.Account
import de.cplaiz.activecraftdashboard.util.SecurityUtils
import de.cplaiz.activecraftdashboard.util.createHexCode
import de.cplaiz.activecraftdashboard.util.genRandString
import de.cplaiz.activecraftdashboard.util.hash
import org.bukkit.Bukkit

class DeviceManager {

    val devices: MutableSet<Device> = Devices.loadDevices().toMutableSet()
    val activeRegistrationCodes = mutableMapOf<String, Account>()

    fun createDeviceId(): String {
        var id = createHexCode()
        while (devices.map { it.id }.contains(id)) {
            id = createHexCode()
        }
        return id
    }

    fun generateRegistrationCode(account: Account): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var code = genRandString(allowedChars, 6)
        while (activeRegistrationCodes.contains(code)) {
            code = genRandString(allowedChars, 6)
        }
        activeRegistrationCodes[code] = account
        Bukkit.getScheduler().runTaskLater(ActiveCraftDashboard.instance, Runnable {
            activeRegistrationCodes.remove(code)
            val msgFormatter = MessageFormatter("code", code);
            if (account.profile.player != null)
                account.profile.player.sendMessage(msgFormatter.format(ActiveCraftDashboard.instance.activeCraftMessage.getMessage("command.register-device.code-expired")))
        }, (20 * ActiveCraftDashboard.instance.mainConfig.accessCodeExpiration).toLong())
        return code
    }

    fun generateAuthToken(): String {
        var token = SecurityUtils.genToken(96)
        while (devices.map { it.token }.contains(token.hash())) {
            token = SecurityUtils.genToken(96)
        }
        return token
    }

    fun registerDevice(account: Account, name: String, platform: Platform): Pair<String, Device> {
        val token = generateAuthToken()
        val device = Device(account, createDeviceId(), name, platform, token.hash())
        devices.add(device)
        Devices.saveDevice(device)
        return token to device
    }

    fun removeDevice(id: String) {
        removeDevice(Device.of(id)!!)
    }

    fun removeDevice(device: Device) {
        devices.remove(device)
        Devices.deleteDevice(device)
    }

}