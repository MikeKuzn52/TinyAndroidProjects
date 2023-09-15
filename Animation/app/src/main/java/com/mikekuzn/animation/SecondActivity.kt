package com.mikekuzn.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d("MikeKuzn", "onCreate")
        findViewById<Button>(R.id.button).setOnClickListener(View.OnClickListener { goToBack() })
    }

    private fun goToBack() {
        Log.d("MikeKuzn", "goToBack")
        finish()
        overridePendingTransition(R.anim.activity_down_up_close_enter, R.anim.activity_down_up_close_exit)
    }
}