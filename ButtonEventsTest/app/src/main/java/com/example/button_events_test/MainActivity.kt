package com.example.button_events_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val controller = InputDeviceEventController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.Down1).setOnClickListener {
            controller.onEvent(1, InEvent.DOWN)
        }
        findViewById<Button>(R.id.Down2).setOnClickListener {
            controller.onEvent(2, InEvent.DOWN)
        }
        findViewById<Button>(R.id.Up1).setOnClickListener {
            controller.onEvent(1, InEvent.UP)
        }
        findViewById<Button>(R.id.Up2).setOnClickListener {
            controller.onEvent(2, InEvent.UP)
        }
    }

}
