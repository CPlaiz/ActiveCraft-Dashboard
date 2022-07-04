package de.cplaiz.activecraftdashboard.monitor

import com.sun.management.OperatingSystemMXBean
import de.cplaiz.activecraftdashboard.account.Permission
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.api.getAuthorized
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import java.lang.management.ManagementFactory
import java.nio.file.Files
import javax.swing.filechooser.FileSystemView

object HardwareMonitor : Routed("/monitor") {

    private const val BYTE_TO_MB = 1048576

    override fun Route.handleReq() {
        getAuthorized("/cpu", Permission.SEE_HARDWARE) {
            call.respondText(getProcessCpuLoad().toString())
        }
        get("/memory") {
            call.respondText(getRam().toString())
        }
        get("/disk") {
            call.respondText(getDiskSpace().toString())
        }

    }

    fun getProcessCpuLoad(): Double {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).cpuLoad
    }

    fun getRam(): List<Long> {
        val runtime = Runtime.getRuntime()
        val used: Long = ((runtime.totalMemory() - runtime.freeMemory()).toDouble() / BYTE_TO_MB).toLong()
        val max: Long = (runtime.totalMemory().toDouble() / BYTE_TO_MB).toLong()
        val free: Long = (runtime.freeMemory().toDouble() / BYTE_TO_MB).toLong()
        return listOf(used, free, max)
    }

    fun getDiskSpace(): List<Long> {
        val fileSystem = FileSystemView.getFileSystemView().defaultDirectory
        val free: Long = fileSystem.freeSpace
        val total: Long = fileSystem.totalSpace
        val minecraft: Long = Files.walk(Bukkit.getWorldContainer().toPath())
            .map { it.toFile() }
            .filter { it.isFile }
            .mapToLong { f -> f.length() }.sum();
        return listOf(minecraft / BYTE_TO_MB, free / BYTE_TO_MB, total / BYTE_TO_MB)
    }

}