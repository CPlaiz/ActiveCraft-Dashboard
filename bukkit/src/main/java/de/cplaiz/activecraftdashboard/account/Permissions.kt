package de.cplaiz.activecraftdashboard.account

enum class Permissions(val permId: String) {

    ADMIN("0"),
    SEE_PROFILE("1"),
    EDIT_PROFILE("2"),
    BAN_PLAYERS("3"),
    WARN_PLAYERS("4"),
    KICK_PLAYERS("5"),
    OP_PLAYERS("6"),
    MANAGE_PLAYERS(
        "${SEE_PROFILE.permId};${EDIT_PROFILE.permId};${BAN_PLAYERS.permId};${WARN_PLAYERS.permId};${KICK_PLAYERS.permId};${OP_PLAYERS.permId}"
    ),
    READ_CHAT("7"),
    WRITE_CHAT("8"),
    SEE_LOGS("9"),
    SEE_CONFIG("10"),
    EDIT_CONFIG("11"),
    SEE_HARDWARE("12"),
    SEE_PLUGINS("13"),
    MANAGE_PLUGINS("14"),
    SEE_PERMISSIONS("15"),
    EDIT_PERMISSIONS("16"),
    MANAGE_WORLDS("17"),
    MANAGE_SERVER("18"),


}