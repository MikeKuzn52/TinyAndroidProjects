package com.mikekuzn.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText


class SecontdainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secontdain)
        Log.d("MikeKuzn", "onCreate")
        findViewById<Button>(R.id.button).setOnClickListener(View.OnClickListener { goToBack() })
    }

    fun goToBack() {
        Log.d("MikeKuzn", "goToBack")
        finish()
        overridePendingTransition(R.anim.activity_down_up_close_enter, R.anim.activity_down_up_close_exit)
    }
}