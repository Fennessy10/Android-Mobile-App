package com.fit2081.myfirstapplication

import android.media.VolumeShaper.Operation
import androidx.compose.runtime.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*

// Define CompositionLocals to hold shared state
val LocalFirstValue = compositionLocalOf { mutableIntStateOf(1) }
val LocalSecondValue = compositionLocalOf { mutableIntStateOf(1) }
val LocalOperation = compositionLocalOf { mutableStateOf("+") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Using a Column to separate the UI components
            Column(
                modifier = Modifier
                    .fillMaxSize() // Make sure the column fills the entire screen
                    .padding(16.dp), // Optional padding to give space around the edges
                verticalArrangement = Arrangement.Center, // Centers the children vertically
                horizontalAlignment = Alignment.CenterHorizontally // Centers the children horizontally
            ) {
                FirstValue() // First component
                Spacer(modifier = Modifier.height(20.dp)) // Add some space between components
                Operation() // Second component
                Spacer(modifier = Modifier.height(20.dp))
                SecondValue();
                Spacer(modifier = Modifier.height(20.dp))
                Text("=")
                Spacer(modifier = Modifier.height(20.dp))
                Result()
            }
        }
    }
}

@Composable
fun Result() {
    val firstValue = LocalFirstValue.current.value
    val secondValue = LocalSecondValue.current.value
    val operation = LocalOperation.current.value

    val result = when (operation) {
        "+" -> firstValue + secondValue
        "-" -> firstValue - secondValue
        else -> 0
    }

    Text(text = result.toString())
}

@Composable
fun FirstValue() {
    val firstValue = LocalFirstValue.current

    Button(onClick = {
        firstValue.value++ // Increment shared state
    }) {
        Text(firstValue.value.toString())
    }
}

@Composable
fun SecondValue() {
    val secondValue = LocalSecondValue.current

    Button(onClick = {
        secondValue.value++ // Increment shared state
    }) {
        Text(secondValue.value.toString())
    }
}

@Composable
fun Operation() {
    val operation = LocalOperation.current

    Button(onClick = {
        operation.value = if (operation.value == "+") "-" else "+"
    }) {
        Text(operation.value)
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewSimpleButton() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FirstValue()
        Spacer(modifier = Modifier.height(20.dp)) // Space between components
        Operation()
    }
}

