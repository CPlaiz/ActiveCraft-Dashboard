package de.cplaiz.activecraftdashboard.profilemanagement

import de.cplaiz.activecraftcore.exceptions.OperationFailureException
import de.cplaiz.activecraftcore.manager.BanManager
import de.cplaiz.activecraftcore.manager.MuteManager
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.api.RequestException
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.util.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import org.bukkit.Location

object PlayerManager : Routed("/profile") {

    override fun Route.handleReq() {
        route("/{name}") {
            get("/") {
                val profile = Profile.of(call.parameters["name"])
                if (profile == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    call.respondText(profile.toJson(), ContentType.Application.Json)
                }
            }
            post("/{action}") {
                val profile = Profile.of(call.parameters["name"])
                if (profile == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    throw RequestException(HttpStatusCode.BadRequest)
                }
                val action = call.parameters["action"]
                val formParameters = call.receiveParameters()
                try {
                    when (action) {
                        "op" -> profile.setOp(formParameters["value"].toBoolean())
                        "nick" -> {
                            profile.set("nickname", formParameters["value"])
                            if (formParameters["update-displayname"].toBoolean())
                                profile.updateDisplayname()
                        }
                        "prefix" -> profile.prefix = formParameters["value"]
                        "mute" -> ActiveCraftDashboard.runTask { MuteManager.mutePlayer(profile) }
                        "unmute" -> ActiveCraftDashboard.runTask { MuteManager.unmutePlayer(profile) }
                        "forcemute" -> ActiveCraftDashboard.runTask { profile.set("forcemuted", true) }
                        "forceunmute" -> ActiveCraftDashboard.runTask { profile.set("forcemuted", false) }
                        "ban" -> BanManager.Name.ban(
                            profile.name,
                            formParameters["reason"],
                            null, // TODO: 25.06.2022 implement settable date
                            "a mod" // TODO: 25.06.2022 put executing acdashboard profile name here
                        )
                        "unban" -> ActiveCraftDashboard.runTask { BanManager.Name.unban(profile.name) }
                        "banip" -> ActiveCraftDashboard.runTask {
                            BanManager.IP.ban(
                                profile.player,
                                formParameters["reason"],
                                null, // TODO: 25.06.2022 implement settable date
                                "a mod" // TODO: 25.06.2022 put executing acdashboard profile name here
                            )
                        }
                        "unbanip" -> ActiveCraftDashboard.runTask { BanManager.IP.unban(profile.player) }
                        "warn" -> {
                            val warnManager = profile.warnManager
                            when (formParameters["mode"]) {
                                "add" -> if (formParameters["source"] == null) warnManager.add(formParameters["reason"]) else warnManager.add(
                                    formParameters["source"],
                                    formParameters["reason"]
                                )
                                "remove" -> warnManager.remove(
                                    warnManager.getWarnById(
                                        formParameters["id"] ?: formParameters["reason"]
                                    )
                                )
                            }
                        }
                        "kick" -> {
                            ActiveCraftDashboard.runTask {
                                val player = profile.player ?: throw RequestException()
                                profile.player.kickPlayer(formParameters["reason"])
                            }
                        }
                        "teleport" -> {
                            val world = Bukkit.getWorld(formParameters["world"] ?: return@post) ?: throw RequestException(HttpStatusCode.BadRequest)
                            val x = (formParameters["x"] ?: throw RequestException()).toDouble()
                            val y = (formParameters["y"] ?: throw RequestException()).toDouble()
                            val z = (formParameters["z"] ?: throw RequestException()).toDouble()
                            val location = Location(world, x, y, z)
                            ActiveCraftDashboard.runTask {
                                val player = profile.player ?: throw RequestException(HttpStatusCode.BadRequest)
                                player.teleport(location)
                            }
                        }
                        else -> call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: OperationFailureException) {
                    call.respond(e.message.toString())
                    return@post
                } catch (e: RequestException) {
                    call.respond(e.httpStatusCode, e.message)
                    return@post
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
