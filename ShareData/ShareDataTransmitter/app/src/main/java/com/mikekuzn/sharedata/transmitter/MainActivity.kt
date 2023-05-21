package com.mikekuzn.sharedata.transmitter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mikekuzn.sharedata.transmitter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onShareAsMessage(view: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, binding.editText.text.toString())
        startActivity(intent)
    }
    fun onShareForReceiver(view: View) {
        val intent = Intent("com.mikekuzn.sharedata.CUSTOM_ACTION")
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        intent.setPackage("com.mikekuzn.sharedata.receiver")
        intent.putExtra("TEST_MESSAGE", binding.editText.text.toString())
        sendBroadcast(intent)
    }
}