package de.cplaiz.activecraftdashboard.monitor

import de.cplaiz.activecraftcore.ActiveCraftCore
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.utils.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import java.util.*

object GameMonitor : RoutedMonitor("/game") {

    override fun Route.handleReq() {
        get("/profile/{name}") {
            val profile = Profile.of(call.parameters["name"])
            if (profile == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respondText(profile.toJson(), ContentType.Application.Json)
            }
        }
        get("/profiles") {
            call.respondText("[${getProfiles().map { it.value.name }.joinToString { it }}]", ContentType.Application.Json)
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