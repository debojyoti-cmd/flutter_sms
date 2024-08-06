import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_sms_platform_interface.dart';

/// An implementation of [FlutterSmsPlatform] that uses method channels.
class MethodChannelFlutterSms extends FlutterSmsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_sms');

  @override
  Future<void> sendSms({
    required String recepient,
    required String message,
    required int slot,
  }) async {
    await methodChannel.invokeMethod<void>('sendSms', {
      'recipient': recepient,
      'message': message,
      'slot': slot,
    });
  }
}
