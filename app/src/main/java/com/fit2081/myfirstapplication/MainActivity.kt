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
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fit2081.myfirstapplication.ui.theme.MyFirstApplicationTheme

class MainActivity : ComponentActivity() { // Welcome Screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        NutritrackTitle()
//                        Spacer(modifier = Modifier.height(10.dp))
                        NutriTrackImage()


                    }
                }
            }
        }
    }
}

@Composable
fun NutritrackTitle(){
    Text(
        "NutriTrack",
        style = TextStyle(
            fontSize = (50.sp),
            fontWeight = (FontWeight.Bold)
        )
    )
}

@Composable
fun NutriTrackImage(){
    androidx.compose.foundation.Image(
        painter = painterResource(id = R.drawable.nutritracklogo),
        contentDescription = "nutritracklogo",
        modifier = Modifier.size(400.dp)
    )

}