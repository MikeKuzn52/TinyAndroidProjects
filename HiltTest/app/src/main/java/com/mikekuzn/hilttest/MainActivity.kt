package com.mikekuzn.hilttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mikekuzn.hilttest.ui.theme.HiltTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var testClass11: TestClass1

    @Inject
    lateinit var testClass12: TestClass1

    @Inject
    lateinit var testClass21: TestClass2

    @Inject
    lateinit var testClass22: TestClass2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TestHilt", "testClass11=$testClass11")
        Log.i("TestHilt", "testClass12=$testClass12")
        Log.i("TestHilt", "testClass21=$testClass21")
        Log.i("TestHilt", "testClass22=$testClass22")
        Log.i("TestHilt", "testClass11 === testClass12 = ${testClass11===testClass12}")
        Log.i("TestHilt", "testClass21 === testClass22 = ${testClass21===testClass22}")
// Основная цель - посмотреть разницу инжекта в конструктор и модуля
// Результат - инжект в конструктор генерирует более оптимальный код фабрики.
        enableEdgeToEdge()
        setContent {
            HiltTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HiltTestTheme {
        Greeting("Android")
    }
}
