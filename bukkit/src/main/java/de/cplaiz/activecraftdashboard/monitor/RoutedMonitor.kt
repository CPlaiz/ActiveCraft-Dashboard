package de.cplaiz.activecraftdashboard.monitor

import io.ktor.server.routing.*

abstract class RoutedMonitor(val path: String) {

    fun route(routing: Routing) : Route {
        return routing.route(path) { handleReq() }
    }

    abstract fun Route.handleReq()
}