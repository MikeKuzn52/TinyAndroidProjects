package com.example.koinmvvmexample.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.koinmvvmexample.presentation.models.MainViewModel
import com.example.koinmvvmexample.presentation.ui.theme.KoinMvvmExampleTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinMvvmExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(vm)
                }
            }
        }
    }
}

@Composable
fun Greeting(vm: MainViewModel) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello! ${vm.getCount().value}",
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Button(onClick = { vm.Restart() }) {
            Text(
                text = "Restart",
                fontSize = 24.sp
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = vm.data.value,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = { vm.Get() }) {
            Text(
                text = "Get data",
                fontSize = 24.sp
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = vm.dataToSave.value,
                onValueChange = { vm.dataToSave.value = it },
                textStyle = TextStyle(fontSize = 24.sp),
                label = { if (vm.dataToSave.value.isEmpty()) {Text(text = "Put data to save")} else null}
            )
        }
        Button(onClick = { vm.Set() }) {
            Text(
                text = "Set data",
                fontSize = 24.sp
            )
        }
    }
}