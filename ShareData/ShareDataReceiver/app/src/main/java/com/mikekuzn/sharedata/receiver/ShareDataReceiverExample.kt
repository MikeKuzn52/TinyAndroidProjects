package com.mikekuzn.sharedata.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ShareDataReceiverExample : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast(context).also {
            val text = intent.getStringExtra("TEST_MESSAGE")
            it.setText("Input shared data \"${text}\"")
            it.duration = Toast.LENGTH_LONG
            it.show()
        }
    }
}