package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore

class ServerManager {

    private val mainConfig = ActiveCraftDashboard.instance.mainConfig

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        if (mainConfig.useHttps) {
            val pw = mainConfig.certPassword.toCharArray()
            val keyStoreFile =
                File("${ActiveCraftDashboard.instance.dataFolder}${File.separator}${mainConfig.certPath}")
            val keystore: KeyStore = KeyStore.getInstance("pkcs12").apply {
                load(FileInputStream(keyStoreFile), pw)
            }
            sslConnector(
                keyStore = keystore,
                keyAlias = mainConfig.certAlias,
                keyStorePassword = { pw },
                privateKeyPassword = { pw }) {
                host = mainConfig.host
                port = mainConfig.port
                keyStorePath = keyStoreFile
            }
        } else {
            connector {
                host = mainConfig.host
                port = mainConfig.port
            }
        }
        module(Application::configureRouting)
    }

    private val server = embeddedServer(Netty, environment = environment)

    fun startServer() {
        Thread {
            server.start(wait = true)
        }.start()
    }

    fun stopServer() {
        server.stop(1000, 1000)
    }
}