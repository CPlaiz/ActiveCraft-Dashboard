package de.cplaiz.activecraftdashboard.monitor

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory
import javax.management.Attribute
import javax.management.ObjectName

object HardwareMonitor {

    fun getProcessCpuLoad() : Double {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).cpuLoad
    }
}