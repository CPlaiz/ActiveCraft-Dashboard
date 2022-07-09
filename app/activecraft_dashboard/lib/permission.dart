import 'package:activecraft_dashboard/account.dart';
import 'package:activecraft_dashboard/server.dart';
import 'package:flutter/material.dart';

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

class Permittable extends StatefulWidget {
  final List<Permission> permissions;
  final Widget child;
  final Account account;

  void update() {
    state.update();
  }

  Permittable(
      {
      required this.child,
      required this.account,
        required this.permissions,
      Key? key})
      : super(key: key);

  Permittable.fromServer(permissions, child, Server server, {Key? key})
      : this(
            permissions: permissions,
            child: child,
            account: server.account,
            key: key);


  final PermittableState state = PermittableState();

  @override
  State<StatefulWidget> createState() => state;
}

class PermittableState extends State<Permittable> {
  late bool shown;

  void update() {
    setState(() {
      shown = widget.account.permissions.containsAll(widget.permissions);
    });
  }

  @override
  Widget build(BuildContext context) {
    update();
    return Visibility(child: widget.child, visible: shown);
  }
}
