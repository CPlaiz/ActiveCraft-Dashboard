import 'package:app/permission.dart';

class User {

  User(this.name, this.deviceId, this.nickname, this.permissions);

  final String deviceId;
  final String name;
  final String nickname;
  final List<Permission> permissions;

}