package com.mikekuzn.ksptest

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import com.mikekuzn.ksptest.ui.theme.KspTestTheme
import com.mikekuzn.ksptest.Animal

class MainActivity : ComponentActivity() {

    private val animals =  AnimalType.entries
        .map { AnimalFactory(it, it.name) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KspTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        animals = animals,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}



@Composable
fun Greeting(animals: List<Animal>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier) {
        animals.forEach {
            Button(onClick = {
                Toast.makeText(context, it.sound(), Toast.LENGTH_SHORT).show()
            }) {
                Text(text = it.myName)
            }
        }
    }
}
