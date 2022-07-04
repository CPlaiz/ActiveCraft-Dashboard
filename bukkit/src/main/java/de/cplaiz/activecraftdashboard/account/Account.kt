package de.cplaiz.activecraftdashboard.account

import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.account.device.Device
import java.util.*

class Account(val profile: Profile) {

    val permissions: MutableSet<Permission> = mutableSetOf()
    val devices: MutableSet<Device> = mutableSetOf()

    companion object {
        fun of(profile: Profile): Account? {
            return ActiveCraftDashboard.instance.accounts[profile]
        }

        fun of(uuid: UUID): Account? {
            return of(Profile.of(uuid))
        }

        fun of(uuid: String): Account? {
            return of(UUID.fromString(uuid))
        }
    }

    fun addPermission(permission: Permission) {
        permissions.add(permission)
    }

    fun removePermission(permission: Permission) {
        permissions.remove(permission)
    }

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

    fun hasPermissions(vararg permissions: Permission): Boolean {
        return this.permissions.containsAll(permissions.toList())
    }

    fun hasPermissions(permissions: List<Permission>): Boolean {
        return this.permissions.containsAll(permissions)
    }

    fun Profile.getAccount() = of(this)
}