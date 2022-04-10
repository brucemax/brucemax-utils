package com.brucemax.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brucemax.brucemax_utils.myLog
import com.brucemax.brucemax_utils.showToast
import com.brucemax.timer.ui.theme.TimerTheme
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Greeting("Max")
                    }
                }
            }
        }
    }
}

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    totalTime: Long,
    initialValue: Float = 1f,
    handleColor: Color = Color.Green,
    activeBarColor: Color = Color.Green,
    inactiveBarColor: Color = Color.Gray,
    strokeWidth: Dp = 5.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100)
            currentTime -= 100
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.onSizeChanged {
            size = it
        }
    ) {
        Canvas(modifier = modifier) {
            val arcWidth = size.width.toFloat()/* - 10.dp.toPx()*/
            val arcHeight = size.height.toFloat()/* - 10.dp.toPx()*/
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(arcWidth, arcHeight),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(arcWidth, arcHeight),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            val center = Offset(size.width/2f, size.height/2f)
            val beta = (250f * value + 145f) * (PI/ 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth*3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        Text(
            text = (currentTime/1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Button(
            onClick = {
                if (currentTime <= 0) {
                    currentTime = totalTime
                    isTimerRunning = true
                } else {
                    isTimerRunning = !isTimerRunning
                }
                context.showToast("Tap!")
                myLog("Tap!")
            }, modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = if (isTimerRunning && currentTime >= 0L) "Stop"
                else if (!isTimerRunning && currentTime >= 0L) "Start"
                else "Restart"
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
//    Text(text = "Hey $name!")
    Timer(modifier = Modifier.size(200.dp),  totalTime = 100000)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimerTheme {
        Greeting("Max")
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    Timer(modifier = Modifier.size(300.dp),  totalTime = 10000)
}