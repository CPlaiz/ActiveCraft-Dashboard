package de.cplaiz.activecraftdashboard.profilemanagement

import de.cplaiz.activecraftcore.exceptions.OperationFailureException
import de.cplaiz.activecraftcore.manager.BanManager
import de.cplaiz.activecraftcore.manager.MuteManager
import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftcore.utils.StringUtils
import de.cplaiz.activecraftdashboard.api.Routed
import de.cplaiz.activecraftdashboard.util.toJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object PlayerManager : Routed("/profile") {

    override fun Route.handleReq() {
        get("/{name}") {
            val profile = Profile.of(call.parameters["name"])
            if (profile == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respondText(profile.toJson(), ContentType.Application.Json)
            }
        }
        post("/{name}") {
            val profile = Profile.of(call.parameters["name"])
            if (profile == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val formParameters = call.receiveParameters()
            try {
                for (formEntry in formParameters.entries()) {
                    when (formEntry.key) {
                        "op" -> profile.setOp(StringUtils.combineList(formEntry.value).toBoolean())
                        "nick" -> profile.set("nickname", StringUtils.combineList(formEntry.value))
                        "prefix" -> profile.prefix = StringUtils.combineList(formEntry.value)
                        "mute" -> if (StringUtils.combineList(formEntry.value).toBoolean()) MuteManager.mutePlayer(profile) else MuteManager.unmutePlayer(profile)
                        "ban" -> BanManager.Name.ban(profile.name, formParameters["reason"], TODO("datum"), TODO("dashboard: connected profile"))
                        "unban" -> BanManager.Name.unban(profile.name)
                        "warn" -> TODO("warn")
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