package de.cplaiz.activecraftdashboard.utils.config

import de.cplaiz.activecraftcore.utils.config.ActiveCraftConfig
import de.cplaiz.activecraftcore.utils.config.FileConfig
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard

class MainConfig : ActiveCraftConfig(FileConfig("config.yml", ActiveCraftDashboard.instance)) {

    var port: Int = 0
        private set
    lateinit var host: String
        private set
    lateinit var certPassword: String
        private set
    lateinit var certPath: String
        private set
    lateinit var certAlias: String
        private set
    var useHttps: Boolean = false
        private set

    override fun load() {
        port = fileConfig.getInt("port")
        host = fileConfig.getString("host") ?: "localhost"
        certPassword = fileConfig.getString("certificate-password") ?: ""
        certPath = fileConfig.getString("certificate-path") ?: "cert.jks"
        certAlias = fileConfig.getString("certificate-alias") ?: "certificate"
        useHttps = fileConfig.getBoolean("use-https")
    }
}