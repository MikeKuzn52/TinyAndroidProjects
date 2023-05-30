package com.example.koinmvvmexample.domain.UseCases

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf

class MyCounter(private val application: Application, val time:Long) {
    var count = mutableStateOf<Long>(time)
    var temer: CountDownTimer? = null

    fun execute(){
        temer?.cancel()
        start()
    }

    fun get() = count

    private fun start() {
        temer = object : CountDownTimer(time, 100) {
            override fun onTick(millisUntilFinished: Long) {
                count.value = millisUntilFinished
            }

            override fun onFinish() {
                count.value = 0
                Toast(application).let {
                    it.setText("The end")
                    it.duration = Toast.LENGTH_LONG
                    it.show()
                }
            }
        }.start()
    }
}