package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftcore.ActiveCraftPlugin
import de.cplaiz.activecraftdashboard.account.AccountRouting
import de.cplaiz.activecraftdashboard.discord.DiscordSettings
import de.cplaiz.activecraftdashboard.monitor.ConsoleMonitor
import de.cplaiz.activecraftdashboard.monitor.GameMonitor
import de.cplaiz.activecraftdashboard.monitor.HardwareMonitor
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {
    routing {
        HardwareMonitor.route(this)
        GameMonitor.route(this)
        ConsoleMonitor.route(this)
        if (ActiveCraftPlugin.isDependancyPresent("ActiveCraft-Discord")) {
            DiscordSettings.route(this)
        }
        AccountRouting.route(this)
    }
}

fun Route.get(path: String, requireAuthorization: Boolean, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    TODO("Implement extension function to always check if the user is authorized")
}