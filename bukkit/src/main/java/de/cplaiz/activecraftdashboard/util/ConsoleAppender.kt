package de.cplaiz.activecraftdashboard.util

import de.cplaiz.activecraftcore.utils.ColorUtils
import de.cplaiz.activecraftdashboard.monitor.ConsoleMonitor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.layout.PatternLayout
import java.util.*


@Plugin(name = "ActiveCraft-Discord-ConsoleChannel", category = "Core", elementType = "appender", printObject = true)
class ConsoleAppender :
    AbstractAppender("ActiveCraft-Discord-ConsoleChannel", null, PatternLayout.createDefaultLayout(), true) {
    init {
        val rootLogger: Logger = LogManager.getRootLogger() as Logger
        start()
        rootLogger.addAppender(this)
    }

    fun shutdown() {
        val rootLogger: Logger = LogManager.getRootLogger() as Logger
        rootLogger.removeAppender(this)
    }

    override fun append(event: LogEvent) {
        val eventLevel: String = event.level.name().uppercase()
        var line: String = event.message.formattedMessage
        line = ColorUtils.removeColorAndFormat(line)
        if (line == "") return
        line = line.trim { it <= ' ' }
        val finalLine = "[" + Date() + ", " + eventLevel + "]  " + line
        ConsoleMonitor.pendingConsoleContent.add(finalLine)
        while (ConsoleMonitor.pendingConsoleContent.size > 100) {
            ConsoleMonitor.pendingConsoleContent.pop()
        }
    }
}