package de.cplaiz.activecraftdashboard.monitor

import de.cplaiz.activecraftcore.ActiveCraftCore
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.profilemanagement.PlayerManager
import de.cplaiz.activecraftdashboard.util.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import java.util.*

object GameMonitor : Routed("/game") {

    override fun Route.handleReq() {
        PlayerManager.route(this)
        get("/profiles") {
            call.respondText("[${getProfiles().map { it.value.name }.joinToString { it }}]", ContentType.Application.Json)
        }
        get("/profiles/online") {
            call.respondText("[${getOnlineProfiles().map { it.value.name }.joinToString { it }}]", ContentType.Application.Json)
        }
        get("/tps") {
            call.respondText(getTPS().toString())
        }
        get("/players") {
            call.respondText(getOnlinePlayers().toString(), ContentType.Application.Json)
        }
        get("/max-player-count") {
            call.respondText(getMaxPlayerCount().toString())
        }
        get("/player-count") {
            call.respondText(getPlayerCount().toString())
        }

    }

    fun getPlayerCount(): Int {
        return Bukkit.getOnlinePlayers().size
    }

    fun getMaxPlayerCount(): Int {
        return Bukkit.getMaxPlayers()
    }

    fun getTPS(): Double {
        return Bukkit.getTPS().average()
    }

    fun getOnlinePlayers(): List<String> {
        return Bukkit.getOnlinePlayers().map { it.name }
    }

    fun getProfiles(): Map<UUID, Profile> {
        return ActiveCraftCore.getInstance().profiles
    }

    fun getOnlineProfiles(): Map<UUID, Profile> {
        return getProfiles().filter { it.value.player != null }
    }
}