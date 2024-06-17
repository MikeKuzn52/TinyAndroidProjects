package com.mikekuzn.testdetectphonecall

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class CallDetector1: BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val event = intent?.getStringExtra(TelephonyManager.EXTRA_STATE);
        val number = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.i("CallDetector", "CallDetector1 event=$event number=$number")
    }
}