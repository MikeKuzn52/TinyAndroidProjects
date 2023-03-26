package com.mikekuzn.examle.duggetrtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var info: Info
    @Inject lateinit var classA: testClassA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)
        val text1 = findViewById<TextView>(R.id.text1)
        val text2 = findViewById<TextView>(R.id.text2)
        val text3 = findViewById<TextView>(R.id.text3)
        text1.text = info.text//tA.toString();
        text2.text = classA.toString();
        text3.text = appComponent.classA.toString();
    }
}


class Info @Inject constructor() {
    val text = "Hello Dagger 2"
}