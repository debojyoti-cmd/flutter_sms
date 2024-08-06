package com.credilio.flutter_sms

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterSmsPlugin */
class FlutterSmsPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_sms")
    channel.setMethodCallHandler(this)
    this.context = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "sendSms") {
      val recipient = call.argument<String>("recipient")
      val message = call.argument<String>("message")
      val slot = call.argument<Int>("slot")
      if (recipient != null && message != null && slot != null) {
        sendSms(context ,message, recipient, slot)
        result.success(null)
      } else {
        result.error("INVALID_ARGUMENTS", "Invalid arguments for sendSms", null)
      }
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun sendSms(context: Context, message: String, recipient: String, simSlot: Int) {
    val subscriptionManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
    val subscriptionInfoList = subscriptionManager.activeSubscriptionInfoList

    if (subscriptionInfoList != null && simSlot < subscriptionInfoList.size) {
      val subscriptionInfo = subscriptionInfoList[simSlot]
      val subscriptionId = subscriptionInfo.subscriptionId

      try {
        val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          context.getSystemService(SmsManager::class.java)
            .createForSubscriptionId(subscriptionId)
        } else {
          SmsManager.getSmsManagerForSubscriptionId(subscriptionId)
        }

        smsManager.sendTextMessage(recipient, null, message, null, null)
      } catch (e: Exception) {
        Log.e("SMS_SEND", "Error sending SMS: ${e.message}")
        // Handle the exception (e.g., show an error message to the user)
      }
    } else {
      Log.e("SMS_SEND", "Invalid SIM slot or no SIM available")
      // Handle the error (e.g., show an error message to the user)
    }
  }

}
