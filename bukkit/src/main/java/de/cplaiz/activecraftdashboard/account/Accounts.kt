package de.cplaiz.activecraftdashboard.account

import de.cplaiz.activecraftcore.exceptions.InvalidPlayerException
import de.cplaiz.activecraftcore.playermanagement.Profile
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object Accounts : Table("accounts") {

    // note: onetimeid -> register device -> get token -> use token for future logins

    val id = integer("id").autoIncrement()
    val uuid = uuid("uuid")
    val permissions = varchar("permissions", 256)

    override val primaryKey = PrimaryKey(id, name = "accounts_pk")

    @kotlin.jvm.Throws(InvalidPlayerException::class)
    fun toAccount(row: ResultRow): Account {
        val profile = Profile.of(row[uuid]) ?: throw InvalidPlayerException("No player with the uuid \"${row[uuid]}\" could be found.", row[uuid].toString())
        return Account(profile, Permission.fromString(row[permissions]))
    }

    fun loadAccounts(): List<Account> {
        return transaction { Accounts.selectAll().map { toAccount(it) } }
    }

    @kotlin.jvm.Throws(InvalidPlayerException::class)
    fun loadAccount(uuid: String): Account? {
        return loadAccount(UUID.fromString(uuid))
    }

    @kotlin.jvm.Throws(InvalidPlayerException::class)
    fun loadAccount(uuid: UUID): Account? {
        return transaction { Accounts.select { Accounts.uuid eq uuid }.map { toAccount(it) }.firstOrNull() }
    }

    @kotlin.jvm.Throws(InvalidPlayerException::class)
    fun createAccount(
        uuid: UUID,
        permissions: Set<Permission>
    ): Account? {
        transaction {
            Accounts.insert {
                it[Accounts.uuid] = uuid
                it[Accounts.permissions] = permissions.joinToString(",") { permission -> permission.ordinal.toString() }
            }
        }
        return loadAccount(uuid)
    }
}

