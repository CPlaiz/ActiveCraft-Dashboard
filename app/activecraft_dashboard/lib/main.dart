import 'package:activecraft_dashboard/account.dart';
import 'package:activecraft_dashboard/permission.dart';
import 'package:activecraft_dashboard/server.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  // TODO: download and upgrade to flutter 3.0.3 / dart 2.17.5
  ActiveCraftDashboard.instance = ActiveCraftDashboard();
  ActiveCraftDashboard.prefs = await SharedPreferences.getInstance();
  ActiveCraftDashboard.instance.startApp();
}

class ActiveCraftDashboard {
  static late ActiveCraftDashboard instance;
  static late SharedPreferences prefs;
  late Map<Server, Account> accounts;

  startApp() {
    accounts = loadAccounts();
    runApp(const MyApp());
  }

  static Map<Server, Account> loadAccounts() {
    prefs.setStringList("servers", [Server(id: "xyz", ip: "123.456.789.000", port: 69, account: Account(name: "big pp cp", deviceId: "xyz", permissions: {Permission.MANAGE_WORLDS, Permission.SEE_LOGS})).toJson()]);
    return {}; // TODO: implement
  }
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            Permittable(
                permissions: const [Permission.ADMIN],
            account: Account(name: "big pp cp", deviceId: "xyz", nickname: "biggus dickus", permissions: {Permission.MANAGE_WORLDS, Permission.SEE_LOGS}),
            child: const Text("pepega")),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
