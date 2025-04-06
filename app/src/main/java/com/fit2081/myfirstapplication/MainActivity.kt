package com.fit2081.myfirstapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                        NutriTrackImage()
                        LoginButton()
                        Spacer(modifier = Modifier.height(50.dp))
                        NutriTrackDescription()
                        Spacer(modifier = Modifier.height(50.dp))
                        Signiture()
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
        modifier = Modifier
            .size(300.dp)
    )
}

@Composable
fun NutriTrackDescription(){
    Text(
    "This app provides general health and nutrition information for\n" +
        "educational purposes only. It is not intended as medical advice,\n" +
        "diagnosis, or treatment. Always consult a qualified healthcare\n" +
        "professional before making any changes to your diet, exercise, or\n" +
        "health regimen.\n" +
        "Use this app at your own risk.\n" +
        "If you’d like to an Accredited Practicing Dietitian (APD), please\n" +
        "visit the Monash Nutrition/Dietetics Clinic (discounted rates for\n" +
        "students):\n" +
        "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
        style = TextStyle(fontStyle = FontStyle.Italic),
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginButton(){
    val context = LocalContext.current // Get the current context

    Button(onClick = {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
    }) {
        Text(
            "Login",
            style = TextStyle(
                fontSize = 60.sp
            )
        )
    }
}

@Composable
fun Signiture(){
    Text(
        "Designed by Patrick Fennessy (33906548)",
        textAlign = TextAlign.Center
    )
}