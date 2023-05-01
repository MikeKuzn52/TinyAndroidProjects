package com.mikekuzn.smstest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView phone, sms, smsReceiveText;
    private Button send, sendByIntent;
    private SmsTransmitter smsTransmitter;
    private Permission permission;
    private final SmsReceiver smsReceiver = new SmsReceiver();
    private final IntentFilter myFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.phone);
        sms = findViewById(R.id.sms);
        send = findViewById(R.id.send);
        sendByIntent = findViewById(R.id.sendByIntent);
        smsReceiveText = findViewById(R.id.smsReceive);
        smsTransmitter = SmsTransmitter.getInstance(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lPhone = phone.getText().toString();
                String lSms = sms.getText().toString();
                smsTransmitter.sendSmsWithConfirmation(lPhone, lSms);
            }
        });
        sendByIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lPhone = phone.getText().toString();
                String lSms = sms.getText().toString();
                Uri uri = Uri.parse("smsto:" + lPhone);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", lSms);
                startActivity(it);
            }
        });
        getPermission(null);
    }

    class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey("pdus")) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0]);
                String senderNumber = sms.getOriginatingAddress();
                //if( senderIsInBlackList(senderNumber)) {
                //    abortBroadcast();
                //}
                smsReceiveText.setText(senderNumber + " " + sms.getMessageBody());
            }

        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(smsReceiver); // UnRegister BroadCastReceiver
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent res = registerReceiver(smsReceiver, myFilter); // Register BroadCastReceiver
    }

    void readSms() {
        Cursor cursor = getContentResolver()
                .query(
                        Uri.parse("content://sms/inbox"),
                        null, null, null, null
                );
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
                Log.d("MikeKuzn", msgData);
            } while (cursor.moveToNext());
        } else {
            Log.d("MikeKuzn", "empty box, no SMS");
        }
    }

    public void getPermission(View view) {
        if (permission == null) {
            permission = new Permission(this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.READ_SMS
                    });
        }
        boolean res = permission.ask();
        Log.d("MikeKuzn",  res ?
                "Permission present" :
                "Permission absent");
        if (res) {
            send.setEnabled(true);
            sendByIntent.setEnabled(true);
            readSms();
        }
    }
}