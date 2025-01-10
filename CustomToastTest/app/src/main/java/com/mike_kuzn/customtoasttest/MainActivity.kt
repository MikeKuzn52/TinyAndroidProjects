package com.mike_kuzn.customtoasttest

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.setPadding
import com.mike_kuzn.customtoasttest.ui.theme.CustomToastTestTheme


class MainActivity : ComponentActivity() {
    var state by mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        f1()
        enableEdgeToEdge()
        setContent {
            CustomToastTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .padding(start = 10.dp)) {
                        Text(text = state)
                        ToastButton("Simple TOAST"){ toast->
                            addCallBack(toast)
                        // Для справки toast.view возвращает null,
                        // т.е. не получится получить доступ к системному LinearLayout и поломать его
                        }
                        ToastButton("ImageView TOAST"){ toast->
                            addCallBack(toast)
                            val imageView = ImageView(applicationContext)
                            imageView.setImageResource(R.drawable.i1)
                            toast.setGravity(Gravity.TOP, 0, 0)
                            toast.view = imageView
                        }
                        ToastButton("Image and text TOAST"){ toast->
                            addCallBack(toast)
                            val linearLayout = LinearLayout(applicationContext)
                            val imageView = ImageView(applicationContext)
                            val textView = TextView(applicationContext)
                            imageView.setImageResource(R.drawable.i2)
                            textView.text = "Image and text TOAST"
                            textView.setTextColor(resources.getColor(R.color.error))
                            linearLayout.setPadding(15)
                            linearLayout.setBackgroundColor(resources.getColor(R.color.black))
                            linearLayout.addView(imageView)
                            linearLayout.addView(textView)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.view = linearLayout
                        }
                        ToastButton("Image and text vertical TOAST"){ toast->
                            addCallBack(toast)
                            val linearLayout = LinearLayout(applicationContext)
                            val imageView = ImageView(applicationContext)
                            val textView = TextView(applicationContext)
                            imageView.setImageResource(R.drawable.i3)
                            textView.text = "Image and text vertical TOAST"
                            textView.setTextColor(resources.getColor(R.color.warning))
                            linearLayout.setPadding(15)
                            linearLayout.setBackgroundColor(resources.getColor(R.color.black))
                            linearLayout.orientation = LinearLayout.VERTICAL
                            linearLayout.addView(imageView)
                            linearLayout.addView(textView)
                            toast.setGravity(Gravity.BOTTOM, 0, 0)
                            toast.view = linearLayout
                        }
                    }
                }
            }
        }
    }
    private fun addCallBack(toast: Toast) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            toast.addCallback(object : Toast.Callback() {
                override fun onToastShown() {
                    super.onToastShown()
                    state = "toast is showing"
                }
                override fun onToastHidden() {
                    super.onToastHidden()
                    state = "toast is hidden"

                }
            })
        }
    }
}

@Composable
fun ToastButton(buttonText: String, run: (toast: Toast) -> Unit) {
    val context = LocalContext.current.applicationContext
    Button(onClick = {
        Toast(context).apply {
            setText(buttonText)
            run(this)
            show()
        }
    }) {
        Text(text = buttonText)
    }
}

fun f1(){ f2() }
fun f2(){
    Log.i("StackTraces","**** NON CLASS ****")
    f3();
    Log.i("StackTraces","**** CLASS ****")
    TmpClas().f3() }
fun f3(){ f4() }
fun f4(){
    val ste = Thread.currentThread().stackTrace
    ste.forEach {
        Log.i("StackTraces","stackTraceElements[0] ${it.methodName} ${it.lineNumber}")
    }
}
class TmpClas{
    fun f3(){ f4() }
}