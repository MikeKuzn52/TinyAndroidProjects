package otus.homework.composemodule

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import otus.homework.composemodule.ui.theme.AnimationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(modifier = Modifier.fillMaxSize()) {
        var imageVisible by remember { mutableStateOf(true) }
        Button(onClick = {
            imageVisible = !imageVisible
        }, Modifier.fillMaxWidth()) {
            Text(text = "Animation 1")
        }

        AnimatedVisibility(
            visible = imageVisible,
            enter = fadeIn(keyframes { this.durationMillis = 5000 }) + expandIn(keyframes { this.durationMillis = 5000 }),
            exit = shrinkOut(keyframes { this.durationMillis = 5000 }) + fadeOut(keyframes { this.durationMillis = 5000 }),

            ) {
            Image(
                painter = painterResource(R.drawable.i),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.Cyan)
                    .padding(10.dp)
                    .background(color = Color.Gray)
                    .padding(15.dp)
                    .fillMaxWidth(1F),
            )
        }
        val shake = remember { Animatable(0f) }
        var trigger by remember { mutableStateOf(0L) }
        LaunchedEffect(trigger) {
            if (trigger != 0L) {
                for (i in 0..10) {
                    when (i % 2) {
                        0 -> shake.animateTo(15f, spring(stiffness = 40_000f))
                        else -> shake.animateTo(-15f, spring(stiffness = 40_000f))
                    }
                }
                shake.animateTo(0f)
            }
        }

        Box(
            modifier = Modifier
                .clickable { trigger += 1 }
                .offset { IntOffset(shake.value.roundToInt(), y = 0) }
                .rotate(-shake.value / 8)
                .padding(20.dp)
        ) {
            Text(
                text = "Shake me",
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = Color.Blue)
                    .padding(15.dp)
                    .fillMaxWidth(1F)
                ,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.android))
        var isPlaying by remember{ mutableStateOf(true) }
        val progress by animateLottieCompositionAsState(
            composition = lottie,
            isPlaying = isPlaying
        )
        Button(onClick = {
            isPlaying = !isPlaying
        }, Modifier.fillMaxWidth()) {
            Text(text = "Start / stop")
        }

        LottieAnimation(composition = lottie, progress = progress)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationTheme {
        Greeting()
    }
}