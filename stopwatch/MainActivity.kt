package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stopwatch.ui.theme.StopwatchTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(StopwatchViewModel::class.java)
        setContent {
            StopwatchTheme {
                StopwatchApp(viewModel)
            }
        }
    }
}

@Composable
fun StopwatchApp(viewModel: StopwatchViewModel) {
    var time by remember { mutableStateOf(viewModel.time) }
    var isRunning by remember { mutableStateOf(viewModel.isRunning) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L)
            time = viewModel.time
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = formatTime(time),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                viewModel.start()
                isRunning = true
            }) {
                Text("Start")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                viewModel.stop()
                isRunning = false
            }) {
                Text("Stop")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                viewModel.reset()
                isRunning = false
                time = 0L
            }) {
                Text("Reset")
            }
        }
    }
}

fun formatTime(time: Long): String {
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
