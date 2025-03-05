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
            }
        }
    }
}

@Composable
fun FirstValue() {
    var count by remember { mutableIntStateOf(1) } // State to remember the count

    Button(onClick = {
        count++;
    }) {
        Text(count.toString());
    }
}

@Composable
fun Operation() {
    var operation by remember { mutableStateOf("+") }

    Button(onClick = {
        if (operation == "+"){
            operation = "-"
        } else if (operation == "-"){
            operation = "+"
        } else {
            error("operation broke")
        }
    }) {
        Text(operation)
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

