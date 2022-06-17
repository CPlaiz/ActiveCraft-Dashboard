package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

class ServerManager {

    private val server = embeddedServer(Netty, port = ActiveCraftDashboard.instance.mainConfig!!.port, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        configureRouting()
    }

    fun startServer() {
        Thread {
            server.start(wait = true)
        }.start()
    }

    fun stopServer() {
        server.stop(1000, 1000)
    }
}