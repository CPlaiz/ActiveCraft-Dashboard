package de.cplaiz.activecraftdashboard.sql

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.account.Accounts
import de.cplaiz.activecraftdashboard.account.device.Devices
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class SQLManager {

    fun init() {
        // connect to sqlite database
        Database.connect("jdbc:sqlite:${ActiveCraftDashboard.instance.dataFolder}/activecraft.db")
        transaction {
            SchemaUtils.create(Accounts, Devices)
        }
    }

}