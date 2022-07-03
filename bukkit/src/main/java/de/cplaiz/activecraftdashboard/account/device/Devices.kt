package de.cplaiz.activecraftdashboard.account.device

import de.cplaiz.activecraftcore.exceptions.InvalidPlayerException
import de.cplaiz.activecraftdashboard.account.Account
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Devices : Table("devices") {

    val id = varchar("id", 6)
    val accountUuid = uuid("account_uuid")
    val name = varchar("name", 32)
    val tokenHash = varchar("token", 256)
    val platform = integer("platform")


    override val primaryKey = PrimaryKey(id, name = "devices_pk")

    @kotlin.jvm.Throws(InvalidPlayerException::class)
    fun toDevice(row: ResultRow): Device {
        return Device(Account.of(row[accountUuid])!!, row[id], row[name], Platform.values()[row[platform]], row[tokenHash])
    }

    fun loadDevices(): List<Device> {
        return transaction { Devices.selectAll().map { toDevice(it) } }
    }

    fun loadDevice(id: String): Device? {
        return transaction { Devices.select { Devices.id eq id }.map { toDevice(it) }.firstOrNull() }
    }

    fun saveDevice(device: Device) {
        transaction {
            val name = device.name
            val accountUuid = device.account.profile.uuid
            val platform = device.platform.ordinal
            println("Saving device $name with accountuuid $accountUuid and platform $platform")
            if (loadDevice(device.id) != null) {
                println("Device already exists. Updating...")
                Devices.update({ Devices.id eq device.id }) {
                    it[this.accountUuid] = accountUuid
                    it[this.name] = name
                    it[this.platform] = platform
                }
            } else {
                println("Device does not exist. Creating...")
                Devices.insert {
                    it[this.id] = device.id
                    it[this.tokenHash] = device.token
                    it[this.accountUuid] = accountUuid
                    it[this.name] = name
                    it[this.platform] = platform
                }
            }
        }
    }

    fun deleteDevice(id: String) {
        transaction {
            Devices.deleteWhere { Devices.id eq id }
        }
    }

    fun deleteDevice(device: Device) {
        transaction {
            Devices.deleteWhere { Devices.id eq device.id }
        }
    }
}