import 'account.dart';

class Server {

  String name;
  String id;
  String ip;
  int port;
  Account account;
  ServerStatus status;

  Server(this.name, this.id, this.ip, this.port, this.account, this.status);
}

enum ServerStatus {
  ONLINE,
  OFFLINE,
  STARTING,
  STOPPING,
  UNKNOWN
}
