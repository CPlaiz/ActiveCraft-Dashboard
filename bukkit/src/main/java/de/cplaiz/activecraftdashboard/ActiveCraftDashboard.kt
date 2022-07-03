package de.cplaiz.activecraftdashboard

import de.cplaiz.activecraftcore.ActiveCraftPlugin
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftcore.utils.config.ConfigManager
import de.cplaiz.activecraftdashboard.account.Account
import de.cplaiz.activecraftdashboard.account.Accounts
import de.cplaiz.activecraftdashboard.account.device.DeviceManager
import de.cplaiz.activecraftdashboard.api.ServerManager
import de.cplaiz.activecraftdashboard.command.RegistrationCodeCommand
import de.cplaiz.activecraftdashboard.command.RestartHTTPServerCommand
import de.cplaiz.activecraftdashboard.sql.SQLManager
import de.cplaiz.activecraftdashboard.util.config.MainConfig

class ActiveCraftDashboard() : ActiveCraftPlugin() {

    private lateinit var servMan: ServerManager //TODO: test if lateinit works; else init null and set in onEnable()
    private lateinit var sqlMan: SQLManager //TODO: test if lateinit works; else init null and set in onEnable()
    lateinit var deviceMan: DeviceManager //TODO: test if lateinit works; else init null and set in onEnable()
    lateinit var mainConfig: MainConfig
    lateinit var accounts: MutableMap<Profile, Account>

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
        servMan.startServer()
        sqlMan = SQLManager()
        sqlMan.init()
        deviceMan = DeviceManager()
        accounts = Accounts.loadAccounts().associateBy { it.profile }.toMutableMap()
    }

    override fun onPluginDisabled() {
        servMan.stopServer()
    }

    override fun registerConfigs() {
        saveDefaultConfig()
        mainConfig = MainConfig()
        ConfigManager.registerConfig(this, mainConfig)
    }

    override fun register() {
        pluginManager.addCommands(RegistrationCodeCommand(this), RestartHTTPServerCommand(this))
    }
}