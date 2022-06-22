package de.cplaiz.activecraftdashboard.monitor

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.util.ConsoleAppender
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import java.util.*


object ConsoleMonitor : Routed("/console"), Listener  {

    val pendingConsoleContent: Deque<String> = LinkedList()
    val consoleAppender: ConsoleAppender = ConsoleAppender()

    override fun Route.handleReq() {
        post {
            val formParameters = call.receiveParameters()
            val message = formParameters["command"]!!
                Bukkit.getScheduler().runTask(ActiveCraftDashboard.instance, Runnable {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message)
                })
            call.respond(HttpStatusCode.OK)
        }
        get {
            call.respondText(pendingConsoleContent.toString(), ContentType.Application.Json)
        }
    }


}