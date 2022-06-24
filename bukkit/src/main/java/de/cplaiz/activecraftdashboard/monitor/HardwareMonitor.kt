package de.cplaiz.activecraftdashboard.monitor

import com.sun.management.OperatingSystemMXBean
import de.cplaiz.activecraftdashboard.api.Routed
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.management.ManagementFactory
import kotlin.math.pow

object HardwareMonitor : Routed("/monitor") {

    override fun Route.handleReq() {
        get("/cpu") {
            call.respondText(getProcessCpuLoad().toString())
        }
        get("/memory") {
            call.respondText(getRam().toString())
        }
    }

    fun getProcessCpuLoad() : Double {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).cpuLoad
    }

    fun getRam() : List<Long>{
        val runtime = Runtime.getRuntime()
        val divisor = 32.0.pow(4.0)
        val used: Long = ((runtime.totalMemory() - runtime.freeMemory()).toDouble() / divisor).toLong()
        val max: Long = (runtime.totalMemory().toDouble() / divisor).toLong()
        val free: Long = (runtime.freeMemory().toDouble() / divisor).toLong()
        return listOf(used, free, max)
    }
}