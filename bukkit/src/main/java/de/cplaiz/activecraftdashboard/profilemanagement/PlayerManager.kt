package de.cplaiz.activecraftdashboard.profilemanagement

import de.cplaiz.activecraftcore.exceptions.OperationFailureException
import de.cplaiz.activecraftcore.manager.BanManager
import de.cplaiz.activecraftcore.manager.MuteManager
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftdashboard.ActiveCraftDashboard
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.util.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit

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
                    return@post
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
                        "mute" -> {
                            Bukkit.getScheduler().runTask(ActiveCraftDashboard.instance, Runnable { MuteManager.mutePlayer(profile) })
                        }
                        "unmute" -> Bukkit.getScheduler().runTask(ActiveCraftDashboard.instance, Runnable { MuteManager.unmutePlayer(profile) })
                        "ban" -> BanManager.Name.ban(
                            profile.name,
                            formParameters["reason"],
                            null, // TODO: 25.06.2022 implement settable date
                            "a mod" // TODO: 25.06.2022 put executing acdashboard profile name here
                        )
                        "unban" -> Bukkit.getScheduler()
                            .runTask(ActiveCraftDashboard.instance, Runnable { BanManager.Name.unban(profile.name) })
                        "ipban" -> BanManager.IP.ban(
                            profile.player,
                            formParameters["reason"],
                            null, // TODO: 25.06.2022 implement settable date
                            "a mod" // TODO: 25.06.2022 put executing acdashboard profile name here
                        )
                        "ipunban" -> Bukkit.getScheduler()
                            .runTask(ActiveCraftDashboard.instance, Runnable { BanManager.IP.unban(profile.player) })
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
                                "get" -> warnManager.getWarnById(formParameters["id"] ?: formParameters["reason"])
                            }
                        }

                    }
                } catch (e: OperationFailureException) {
                    call.respond(e.message.toString())
                    return@post
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}