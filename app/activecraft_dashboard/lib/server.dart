import 'account.dart';
import 'dart:convert';

class Server {

  String name;
  String id;
  String ip;
  int port;
  Account account;
  ServerStatus status;

  Server({this.name = "Minecraft Server", required this.id, required this.ip, required this.port, required this.account, this.status = ServerStatus.OFFLINE});

  String toJson() {
    return json.encode({
      "name": name,
      "id": id,
      "ip": ip,
      "port": port,
      "account": account.toJson(),
      "status": status.index
    });
  }

  static Server fromJson(String json) {
    Map<String, dynamic> map = jsonDecode(json);
    return Server(
      name: map["name"],
      id: map["id"],
      ip: map["ip"],
      port: map["port"],
      account: Account.fromJson(map["account"]),
      status: ServerStatus.values[map["status"]]
    );
  }
}

enum ServerStatus {
  ONLINE,
  OFFLINE,
  STARTING,
  STOPPING,
  UNKNOWN
}
