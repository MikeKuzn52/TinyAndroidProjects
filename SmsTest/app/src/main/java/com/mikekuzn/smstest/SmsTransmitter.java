package com.mikekuzn.smstest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsTransmitter {
    @SuppressLint("StaticFieldLeak")
    private static SmsTransmitter instance = null;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    public static synchronized  SmsTransmitter getInstance(Activity activity) {
        if (instance == null) {
            Log.d("MikeKuzn",  "Create");
            instance = new SmsTransmitter();
        }
        Log.d("MikeKuzn",  "Set activity");
        SmsTransmitter.activity = activity;
        return instance;
    }

    public void sendSms(String phoneNumber, String message) {
        Log.d("MikeKuzn", "Before");
        SmsManager.getDefault().sendTextMessage(phoneNumber,null, message, null, null);
        Log.d("MikeKuzn", "After");
    }


    public void sendSmsWithConfirmation(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
                new Intent(DELIVERED), 0);

        //---когда SMS отправлено---
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                Log.d("MikeKuzn",  "onReceive");
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.d("MikeKuzn",  "SMS sent");
                        Toast.makeText(activity.getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.d("MikeKuzn",  "Generic failure");
                        Toast.makeText(activity.getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.d("MikeKuzn", "No service");
                        Toast.makeText(activity.getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.d("MikeKuzn", "Null PDU");
                        Toast.makeText(activity.getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.d("MikeKuzn", "Radio off");
                        Toast.makeText(activity.getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---когда SMS доставлено---
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                Log.d("MikeKuzn", "onReceive");
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.d("MikeKuzn", "SMS delivered");
                        Toast.makeText(activity.getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d("MikeKuzn", "SMS not delivered");
                        Toast.makeText(activity.getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        Log.d("MikeKuzn", "Before WithConfirmation");
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        Log.d("MikeKuzn", "After WithConfirmation");
    }
}
