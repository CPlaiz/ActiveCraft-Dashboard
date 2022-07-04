package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftcore.ActiveCraftPlugin
import de.cplaiz.activecraftdashboard.account.AccountRouting
import de.cplaiz.activecraftdashboard.account.Permission
import de.cplaiz.activecraftdashboard.account.device.Device
import de.cplaiz.activecraftdashboard.discord.DiscordSettings
import de.cplaiz.activecraftdashboard.monitor.ConsoleMonitor
import de.cplaiz.activecraftdashboard.monitor.GameMonitor
import de.cplaiz.activecraftdashboard.monitor.HardwareMonitor
import de.cplaiz.activecraftdashboard.util.hash
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
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

fun Route.getAuthorized(path: String, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return getAuthorized(path, listOf(), body)
}

fun Route.getAuthorized(path: String, permissions: List<Permission>, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return get(path) { unit ->
        if (callAuthorized(call, permissions)) body(unit) else call.respond(HttpStatusCode.Unauthorized)
    }
}

fun callAuthorized(call: ApplicationCall): Boolean {
    return callAuthorized(call, listOf())
}

fun callAuthorized(call: ApplicationCall, requiredPermissions: List<Permission>): Boolean {
    if (!callAuthorized(call)) return false
    val deviceId = call.request.cookies["activecraft_dashboard_device_id"] ?: return false
    val token = call.request.cookies["activecraft_dashboard_token"] ?: return false
    val device = Device.of(deviceId) ?: return false
    if (device.token != token.hash()) return false
    return requiredPermissions.isEmpty() || device.account.hasPermissions(requiredPermissions)
}