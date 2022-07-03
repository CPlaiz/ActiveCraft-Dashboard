package de.cplaiz.activecraftdashboard.command

import de.cplaiz.activecraftcore.ActiveCraftPlugin
import de.cplaiz.activecraftcore.commands.ActiveCraftCommand
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.account.Account
import de.cplaiz.activecraftdashboard.account.Accounts
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class RegistrationCodeCommand(plugin: ActiveCraftPlugin) : ActiveCraftCommand("register-device", plugin, "device.register") {
    override fun runCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) {
        checkPermission(sender)
        val profile = getProfile(sender)
        val account = Account.of(profile) ?: Accounts.createAccount(profile.uuid, setOf())!!
        val code = ActiveCraftDashboard.instance.deviceMan.generateRegistrationCode(account)
        messageFormatter.addReplacements("code", code, "expires", ActiveCraftDashboard.instance.mainConfig.accessCodeExpiration.toString())
        sendMessage(sender, cmdMsg("code-generated"))
    }

    override fun onTab(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): MutableList<String> {
        return mutableListOf()
    }
}