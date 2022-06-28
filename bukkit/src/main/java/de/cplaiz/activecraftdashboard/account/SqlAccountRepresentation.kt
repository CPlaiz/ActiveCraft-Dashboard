package de.cplaiz.activecraftdashboard.account

import org.jetbrains.exposed.sql.Table

object SqlAccountRepresentation: Table("accounts") {

    val name = varchar("name", 16)
    val uuid = varchar("uuid", 36)
    val tokenHash = varchar("token", 64)
    val permissions = varchar("permissions", 256)


}