package de.cplaiz.activecraftdashboard.monitor

import java.lang.management.ManagementFactory
import java.util.Optional
import javax.management.Attribute
import javax.management.ObjectName

class HardwareMonitor {

    fun getProcessCpuLoad() : Double {
        try {
            val mbs = ManagementFactory.getPlatformMBeanServer();
            val name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            val list = mbs.getAttributes(name, arrayOf("ProcessCpuLoad"));

            return if (list.isEmpty()) -1.0 else list
                .map {  }
                .map{it.iterator()}
                .map{ it.next() }
                .map{ it as Attribute }
                .map { it.value }
                .map { it as Double }
    }

}