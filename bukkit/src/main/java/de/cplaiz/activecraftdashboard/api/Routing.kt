package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftdashboard.monitor.GameMonitor
import de.cplaiz.activecraftdashboard.monitor.HardwareMonitor
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        HardwareMonitor.route(this)
        GameMonitor.route(this)
    }
    routing {

    }
}
