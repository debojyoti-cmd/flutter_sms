import 'package:flutter/material.dart';
import 'package:flutter_sms/flutter_sms.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  var smsHelper = FlutterSms();
  var simSlot = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            smsHelper.sendSms(
              recepient: "+918799728754",
              message: "Message from slot : $simSlot",
              slot: simSlot,
            );
          },
          child: const Icon(Icons.send),
        ),
      ),
    );
  }
}
