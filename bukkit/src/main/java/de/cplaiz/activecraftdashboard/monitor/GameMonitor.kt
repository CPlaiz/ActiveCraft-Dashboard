package de.cplaiz.activecraftdashboard.monitor

import de.silencio.activecraftcore.ActiveCraftCore
import de.silencio.activecraftcore.playermanagement.Profile
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit

object GameMonitor : RoutedMonitor("/game") {

    override fun Route.handleReq() {
        get("/profiles") {
            call.respond(getProfiles())
        }
    }

    fun getPlayerCount() : Int {
        return Bukkit.getOnlinePlayers().size
    }

    fun getMaxPlayerCount() : Int {
        return Bukkit.getMaxPlayers()
    }

    fun getTPS() : Double {
        return Bukkit.getTPS().average()
    }

    fun getOnlinePlayers() : List<String> {
        return Bukkit.getOnlinePlayers().map { it.name }
    }

    fun getProfiles() : Map<String, Profile> {
        return ActiveCraftCore.getInstance().profiles
    }

    fun getOnlineProfiles() : Map<String, Profile> {
        return getProfiles().filter { it.value.player != null }
    }

}