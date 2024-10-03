package com.mikekuzn.readsystemsettings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mikekuzn.readsystemsettings.ui.theme.ReadSystemSettingsTheme


class MainActivity : ComponentActivity() {

    private var canWrite by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        canWrite = Settings.System.canWrite(this)
        Log.i("ReadSystemSettingsTest", "canWrite=$canWrite")
        setContent {
            ReadSystemSettingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = { checkSystemWritePermission() }) {
                            Text(text = "check System Write Permission")
                        }
                        SettingsShowValue(
                            key = Settings.System.SCREEN_OFF_TIMEOUT,
                            name = "screen_off_timeout",
                            defVal = 0,
                            canWrite = canWrite,
                        )
                        SettingsShowValue(
                            key = Settings.System.AIRPLANE_MODE_ON,
                            defVal = false,
                            canWrite = canWrite,
                        )
                        // Моя кастомная настройка. Приложение используется для проверки работы моей настройки в:
                        //   packages/apps/Settings/src/com/android/settings/metrics/MetricsPreferenceController.java
                        SettingsShowValue(
                            key = "metrics_custom_events_enable",
                            defVal = 0,
                            canWrite = canWrite,
                        )
                    }
                }
            }
        }
    }

    private fun checkSystemWritePermission() {
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {canWrite = true; return}
        var retVal = Settings.System.canWrite(this)
        Log.i("ReadSystemSettingsTest", "Can Write Settings: $retVal")
        if (retVal) {
            Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + this.packageName))
            startActivity(intent)
        }
        canWrite = retVal
    }
}

@Composable
fun <T> SettingsShowValue(key: String, name: String? = null, defVal: T, canWrite: Boolean) {
    val context = LocalContext.current
    var value by remember { mutableStateOf(defVal) }
    var error by remember { mutableStateOf(false) }
    var putResult by remember { mutableStateOf("") }
    var initialized by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            try {
                when (value) {
                    is Int ->
                        value = Settings.System.getInt(context.contentResolver, key) as T

                    is Boolean ->
                        value = (Settings.System.getInt(context.contentResolver, key) != 0) as T
                }
                error = false
                initialized = true
            } catch (e: Exception) {
                error = true
            }
        }) {
            Text(text = "Обновить")
        }
        Text(
            modifier = Modifier.padding(10.dp),
            text = "${name ?: key} = ${if (!error) value.toString() else "Error"}"
        )
        if (initialized && canWrite) {
            Button(onClick = {
                try {
                    when (value) {
                        is Int ->
                            Settings.System.putInt(context.contentResolver, key, (value as Int + 1))

                        is Boolean -> {
                            Settings.System.putInt(
                                context.contentResolver,
                                key,
                                if (value as Boolean) 0 else 1
                            )
                        }
                    }
                    putResult = "V"
                } catch (e: Exception) {
                    Log.e("ReadSystemSettingsTest", e.toString())
                    putResult = "E"
                }
            }) {
                Text(text = "Increment")
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = putResult,
            )
        }
    }
}
