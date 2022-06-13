package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class ServerManager {

    private val server = embeddedServer(Netty, port = ActiveCraftDashboard.instance.mainConfig!!.port, host = "127.0.0.1") { configureRouting() }

    fun startServer() {
        server.start(wait = true)
    }

    fun stopServer() {
        server.stop(1000, 1000)
    }

}