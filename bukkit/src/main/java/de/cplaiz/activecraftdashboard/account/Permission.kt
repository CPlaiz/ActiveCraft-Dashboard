package de.cplaiz.activecraftdashboard.account

enum class Permission {

    ADMIN,
    SEE_PROFILE,
    EDIT_PROFILE,
    BAN_PLAYERS,
    WARN_PLAYERS,
    KICK_PLAYERS,
    OP_PLAYERS,
    READ_CHAT,
    WRITE_CHAT,
    SEE_LOGS,
    SEE_CONFIG,
    EDIT_CONFIG,
    SEE_HARDWARE,
    SEE_PLUGINS,
    MANAGE_PLUGINS,
    SEE_PERMISSIONS,
    EDIT_PERMISSIONS,
    MANAGE_WORLDS,
    MANAGE_SERVER;

    companion object {
        fun fromString(s: String): MutableSet<Permission> {
            return s.split(",").map { values()[it.toInt()] }.toMutableSet()
        }

        fun toString(permissions: Set<Permission>): String {
            return permissions.map { it.ordinal }.joinToString(",")
        }
    }
}