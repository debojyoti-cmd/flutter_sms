import 'flutter_sms_platform_interface.dart';

class FlutterSms {
  Future<void> sendSms({
    required String recipient,
    required String message,
    required int slot,
  }) {
    return FlutterSmsPlatform.instance.sendSms(
      recipient: recipient,
      message: message,
      slot: slot,
    );
  }
}
