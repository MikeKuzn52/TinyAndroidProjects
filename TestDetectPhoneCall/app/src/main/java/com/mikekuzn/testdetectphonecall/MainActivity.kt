package com.mikekuzn.testdetectphonecall

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mikekuzn.testdetectphonecall.ui.theme.TestDetectPhoneCallTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestDetectPhoneCallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }


        if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
            Toast.makeText(this, "get permission", Toast.LENGTH_SHORT).show()
        }

    }
}

@SuppressLint("MissingPermission")
@Composable
fun Greeting() {
    val context = LocalContext.current
    val callDetector2 = CallDetector2()
    val callDetector3 = CallDetector3 {
        Log.i("CallDetector", "CallDetector3 event")
    }
    Row {
        Button(onClick = {
            callDetector2.subscribe(context)
            callDetector3.subscribe(context)
        }) {
            Text(text = "Subscribe")
        }
        Button(onClick = {
            callDetector2.unsubscribe(context)
            callDetector3.unsubscribe(context)
        }) {
            Text(text = "Unsubscribe")
        }
    }
}
