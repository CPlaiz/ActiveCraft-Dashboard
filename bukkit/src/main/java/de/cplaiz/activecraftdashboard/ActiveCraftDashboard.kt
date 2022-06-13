package de.cplaiz.activecraftdashboard

import de.cplaiz.activecraftdashboard.api.ServerManager
import de.cplaiz.activecraftdashboard.utils.config.MainConfig
import de.silencio.activecraftcore.ActiveCraftPlugin
import org.bukkit.Bukkit

class ActiveCraftDashboard : ActiveCraftPlugin() {

    private val servMan: ServerManager = ServerManager()
    val mainConfig: MainConfig = MainConfig()

    companion object {
        lateinit var instance: ActiveCraftDashboard
    }

    init {
        instance = this
    }

    override fun onEnable() {
        servMan.startServer()
        Bukkit.getConsoleSender()
    }

    override fun onDisable() {
        servMan.stopServer()
    }

    override fun registerConfigs() {
        TODO("Not yet implemented")
    }

    override fun register() {
        TODO("Not yet implemented")
    }
}