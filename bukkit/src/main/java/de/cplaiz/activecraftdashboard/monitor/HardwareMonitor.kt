package de.cplaiz.activecraftdashboard.monitor

import com.sun.management.OperatingSystemMXBean
import de.cplaiz.activecraftdashboard.api.Routed
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.management.ManagementFactory

object HardwareMonitor : Routed("/monitor") {

    override fun Route.handleReq() {
        get("/cpu") {
            call.respondText(getProcessCpuLoad().toString())
        }
        get("/memory") {
            call.respondText("OK")
        }
    }

    fun getProcessCpuLoad() : Double {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).cpuLoad
    }
}