import 'dart:convert';
import 'package:activecraft_dashboard/permission.dart';

class Account {

  Account({required this.name, required this.deviceId, nickname, required this.permissions}) : nickname = nickname ?? name;

  final String deviceId;
  final String name;
  final String nickname;
  final Set<Permission> permissions;

  String toJson() {
    return json.encode({
      "name": name,
      "device-id": deviceId,
      "nickname": nickname,
      "permissions": permissions.map((p) => p.index).toList()
    });
  }

  static fromJson(String json) {
    Map<String, dynamic> map = jsonDecode(json);
    Set<Permission> permissions = {};
    for (final perm in map["permissions"]) {
      permissions.add(Permission.values[perm]);
    }
    return Account(
      name: map["name"],
      deviceId: map["device-id"],
      nickname: map["nickname"],
      permissions: permissions
    );
  }

}