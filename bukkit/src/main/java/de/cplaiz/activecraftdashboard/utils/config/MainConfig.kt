package de.cplaiz.activecraftdashboard.utils.config

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.silencio.activecraftcore.utils.config.ActiveCraftConfig
import de.silencio.activecraftcore.utils.config.FileConfig

class MainConfig : ActiveCraftConfig(FileConfig("config.yml", ActiveCraftDashboard.instance)) {

    var port: Int = 0
        private set

    override fun load() {
        port = fileConfig.getInt("port")
    }

}