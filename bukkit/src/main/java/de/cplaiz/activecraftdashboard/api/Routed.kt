package de.cplaiz.activecraftdashboard.api

import io.ktor.server.routing.*

abstract class Routed(val path: String) {

    fun route(route: Route) : Route {
        return route.route(path) { handleReq() }
    }

    abstract fun Route.handleReq()
}