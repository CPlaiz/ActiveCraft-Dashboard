package de.cplaiz.activecraftdashboard.api

import de.cplaiz.activecraftcore.exceptions.ActiveCraftException
import io.ktor.http.*

class RequestException(override val message: String) : ActiveCraftException(message) {

    var httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest

    constructor(httpStatusCode: HttpStatusCode, message: String) : this(message) {
        this.httpStatusCode = httpStatusCode
    }

    constructor(httpStatusCode: HttpStatusCode) : this("") {
        this.httpStatusCode = httpStatusCode
    }

    constructor() : this("")

}