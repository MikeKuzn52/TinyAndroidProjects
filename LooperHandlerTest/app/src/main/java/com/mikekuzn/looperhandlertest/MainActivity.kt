package com.mikekuzn.looperhandlertest

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mikekuzn.looperhandlertest.ui.theme.LooperHandlerTestTheme

const val TAG = "LooperHandlerTag"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LooperHandlerTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Button(onClick = {
            while (true) {
                Log.d(TAG, "Before Looper.loop()")
                Looper.loop()
                Log.d(TAG, "This log should never show")
                throw Exception("This exception should never launch")
            }
        }) {
            Text("MainLooper.loop()", fontSize = 25.sp)
        }
        Button(onClick = {
            Looper.getMainLooper().setMessageLogging{
                Log.d(TAG, "Test $it")
            }
        }) {
            Text("Subscribe on looper", fontSize = 25.sp)
        }
        Button(onClick = {
            Looper.myQueue().addIdleHandler{
                Log.d(TAG, "idling")
                // to repeat run this function: return false
                // to stop running this function: return true
                return@addIdleHandler true
            }
        }) {
            Text("Subscribe on looper idling", fontSize = 25.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LooperHandlerTestTheme {
        Greeting("Android")
    }
}