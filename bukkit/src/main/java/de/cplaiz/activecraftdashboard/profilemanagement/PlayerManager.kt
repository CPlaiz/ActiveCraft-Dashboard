package de.cplaiz.activecraftdashboard.profilemanagement

import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.util.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerManager : Routed("/profile") {

    override fun Route.handleReq() {
        get("/{name}") {
            val profile = Profile.of(call.parameters["name"])
            if (profile == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respondText(profile.toJson(), ContentType.Application.Json)
            }
        }
        post("/{name}") {
            val profile = Profile.of(call.parameters["name"])
            val formParameters = call.receiveParameters()
            val actions: Map<String, Unit> = mapOf("op" to (::op)(profile.player))
            for (val action in actions) {
                action.value.apply {  }
            }
        }
    }

    fun op(player: Player) {

    }
}