package de.cplaiz.activecraftdashboard.discord

import de.cplaiz.activecraftdashboard.api.Routed
import io.ktor.server.routing.*

object DiscordSettings : Routed("/discord"){
    override fun Route.handleReq() {
        post {
            
        }
    }
}