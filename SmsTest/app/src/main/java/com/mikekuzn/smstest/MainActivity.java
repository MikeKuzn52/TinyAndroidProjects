package com.mikekuzn.smstest;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView phone, sms;
    Button send;
    SmsTransmitter smsTransmitter;
    Permission permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.phone);
        sms = findViewById(R.id.sms);
        send = findViewById(R.id.send);
        permission = new Permission(this, Manifest.permission.SEND_SMS);
        smsTransmitter = SmsTransmitter.getInstance(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lPhone = phone.getText().toString();
                String lSms = sms.getText().toString();
                smsTransmitter.sendSmsWithConfirmation(lPhone, lSms);
            }
        });
        getPermission(null);
    }

    public void getPermission(View view) {
        if (permission == null) {
            permission = new Permission(this, Manifest.permission.SEND_SMS);
        }
        boolean res = permission.ask();
        Log.d("MikeKuzn",  res ?
                "Permission present" :
                "Permission absent");
        if (res) {
            send.setEnabled(true);
        }
    }
}