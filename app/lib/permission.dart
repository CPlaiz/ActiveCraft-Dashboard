enum Permission {

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
  MANAGE_SERVER
}

abstract class PermittableWidget {

  const PermittableWidget(Permission permission) {

  }



}