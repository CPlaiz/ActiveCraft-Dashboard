package de.cplaiz.activecraftdashboard

import de.cplaiz.activecraftdashboard.api.ServerManager
import de.cplaiz.activecraftdashboard.utils.config.MainConfig
import de.cplaiz.activecraftcore.ActiveCraftPlugin
import de.cplaiz.activecraftcore.utils.config.ConfigManager
import org.bukkit.Bukkit

class ActiveCraftDashboard() : ActiveCraftPlugin() {

    private var servMan: ServerManager? = null
    var mainConfig: MainConfig? = null

    companion object {
        lateinit var instance: ActiveCraftDashboard
            private set
    }

    init {
        instance = this
    }

    override fun onPluginEnabled() {
        checkForUpdate()
        servMan = ServerManager()
        servMan!!.startServer()
        Bukkit.getConsoleSender()
    }

    override fun onPluginDisabled() {
        servMan!!.stopServer()
    }

    override fun registerConfigs() {
        saveDefaultConfig()
        mainConfig = MainConfig()
        ConfigManager.registerConfig(this, mainConfig)
    }

    override fun register() {

    }
}