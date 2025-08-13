package com.mike_kuzn.suspendfun_ut_example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mike_kuzn.suspendfun_ut_example.ui.theme.SuspendFunUT_ExampleTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TAG = "TEST"

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuspendFunUT_ExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

var scope: CoroutineScope? = null
val flow = MutableStateFlow(0)

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(modifier) {
        Button(onClick = {
            Log.d(TAG, "scope?.cancel and create new")
            scope?.cancel()
            scope = CoroutineScope(Dispatchers.Default)
        }) {
            Text(text = "create scope")
        }
        Button(onClick = {
            Log.d(TAG, "scope?.cancel")
            scope?.cancel()
        }) {
            Text(text = "close scope")
        }
        Button(onClick = {
            val t = flow.value + 1
            Log.d(TAG, "tryEmit $t")
            flow.tryEmit(t)
        }) {
            Text(text = "emit")
        }
        Button(onClick = {
            Log.d(TAG, "collect")
            scope?.launch {
                flow.collect {
                    Log.d(TAG, "collect $it")
                }
            }
        }) {
            Text(text = "collect")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuspendFunUT_ExampleTheme {
        Greeting()
    }
}

/*
 Результат в логе:
scope?.cancel and create new
collect
collect 0
tryEmit 1
collect 1
tryEmit 2
collect 2
scope?.cancel - отменили скоуп
tryEmit 3 - т.е. collect отключился т.к. отменился скоуп и нет необходимости стопать джобу
tryEmit 4
tryEmit 5
scope?.cancel and create new - создали новый скоуп
tryEmit 6
collect - подписались на изменения под новым скоупом
tryEmit 7
collect 7
tryEmit 8
collect 8
tryEmit 9
collect 9
*/
