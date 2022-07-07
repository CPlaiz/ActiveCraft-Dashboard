import 'package:activecraft_dashboard/permission.dart';

class Account {

  Account(this.name, this.deviceId, this.nickname, this.permissions);

  final String deviceId;
  final String name;
  final String nickname;
  final List<Permission> permissions;

}