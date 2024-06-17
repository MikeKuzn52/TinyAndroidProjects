package com.mikekuzn.testdetectphonecall

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.TelephonyManager
import android.util.Log

@Suppress("DEPRECATION")
class CallDetector2 {

    private var mReceiver: BroadcastReceiver? = null

    @SuppressLint("InlinedApi")
    fun subscribe(context: Context) {
        if (mReceiver == null) {
            mReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                   val event = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
                   val number = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                   Log.i("CallDetector", "CallDetector2 event=$event number=$number")
                }
            }
            context.registerReceiver(
                mReceiver,
                IntentFilter()
                    .apply { addAction(android.Manifest.permission.READ_PHONE_STATE) },
                Context.RECEIVER_EXPORTED
            )
            Log.i("CallDetector", "CallDetector2 subscribed")
        }
    }

    fun unsubscribe(context: Context) {
        mReceiver?.let {
            context.unregisterReceiver(it)
            mReceiver = null
            Log.i("CallDetector", "CallDetector2 unsubscribed")
        }
    }
}