import 'package:app/permission.dart';

class User {

  User(this.name, this.uuid, this.nickname, this.permissions);

  final String uuid;
  final String name;
  final String nickname;
  final List<Permission> permissions;

}