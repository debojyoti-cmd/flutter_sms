import 'flutter_sms_platform_interface.dart';

class FlutterSms {
  Future<void> sendSms({
    required String recepient,
    required String message,
    required int slot,
  }) {
    return FlutterSmsPlatform.instance.sendSms(
      recepient: recepient,
      message: message,
      slot: slot,
    );
  }
}
