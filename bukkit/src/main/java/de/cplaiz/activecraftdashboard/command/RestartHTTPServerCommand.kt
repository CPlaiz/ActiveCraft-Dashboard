package de.cplaiz.activecraftdashboard.command

import de.cplaiz.activecraftcore.commands.ActiveCraftCommand
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class RestartHTTPServerCommand : ActiveCraftCommand("restart-http-server", ActiveCraftDashboard.instance) {
    override fun runCommand(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun onTab(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): MutableList<String> {
        TODO("Not yet implemented")
    }
}