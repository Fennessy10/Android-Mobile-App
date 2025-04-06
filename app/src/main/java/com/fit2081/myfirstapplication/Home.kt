package com.fit2081.myfirstapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fit2081.myfirstapplication.ui.theme.MyFirstApplicationTheme
import java.io.BufferedReader
import java.io.InputStreamReader

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieve the user ID from the intent
        val userId = intent.getStringExtra("USER_ID") ?: ""

        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    bottomBar = { BottomBar(userId) }
                ) {
                    innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        Greeting("Patrick")
                        Spacer(modifier = Modifier.height(12.dp))
                        Questionnaire(modifier = Modifier, userId)
                        MainImage()
                        Scores(modifier = Modifier, userId)
                        Spacer(modifier = Modifier.height(12.dp))
                        ScoreDescription()

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = "Hello,",
                color = Color.Gray,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
        Row {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun Questionnaire(modifier: Modifier = Modifier, userId: String){
    val context = LocalContext.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.weight(1f) // ensures text doesn't take up all space
        ) {
            Text(
                "You've already filled in your Food Intake Questionnaire, but you can change the details here:",
                style = TextStyle(
                    fontSize = 14.sp
                )
            )

        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(
                onClick = {
                    val intent = Intent(context, FoodIntakeQuestionnaire::class.java).apply {
                        putExtra("USER_ID", userId)  // add user ID to intent
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            )  { Text(
                "Edit Questionnaire"
            ) }
        }
    }
}

@Composable
fun MainImage(){
    Image(
        painter = painterResource(id = R.drawable.dietcirclediagram),
        contentDescription = "Diet Circle",
        modifier = Modifier.size(380.dp)
    )
}

@Composable
fun Scores(modifier: Modifier = Modifier, userId: String){
    val context = LocalContext.current
    val totalScore = remember(userId) { getTotalScore(context, userId) }

    Column (
        modifier = modifier,
    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "My Score",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.weight(1f)

            )
            Button(
                onClick = {
                    val intent = Intent(context, Score::class.java).apply {
                        putExtra("USER_ID", userId)  // add user ID to intent
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("See all scores")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                totalScore,
                color = Color.Blue,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp
                ),

            )
        }
    }
}


@Composable
fun ScoreDescription(modifier: Modifier = Modifier){
    Column (modifier = modifier){
        Row (

        ){
            Text(
                "What is the food Quality Score?",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Row (

        ){
            Text(
                "Your Food Quality Score provides a snapshot of how well your  eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }
        Row (

        ){
            Text(
                "This personalised measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights form making healthier food choices",
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun BottomBar(userId: String) {
    val context = LocalContext.current
    BottomAppBar(
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        val intent = Intent(context, Home::class.java).apply {
                            putExtra("USER_ID", userId)  // add user ID to intent
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(90.dp) // Match BottomAppBar height
                        .width(90.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.homeicon),
                            contentDescription = "Go Home",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "Home",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp) // Small space between icon and text
                        )
                    }
                }
                IconButton(
                    onClick = {
                        val intent = Intent(context, Score::class.java).apply {
                            putExtra("USER_ID", userId)  // add user ID to intent
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(90.dp) // Match BottomAppBar height
                        .width(100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.scoreicon),
                            contentDescription = "Insights",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "Insights",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp) // Small space between icon and text
                        )
                    }
                }
                IconButton( // nutricoach
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .height(90.dp) // Match BottomAppBar height
                        .width(100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.nutricoachicon),
                            contentDescription = "Nutricoach",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "NutriCoach",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp) // Small space between icon and text
                        )
                    }
                }
                IconButton( // settings
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .height(60.dp) // Match BottomAppBar height
                        .width(100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settingsicon),
                            contentDescription = "settings",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "Settings",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp) // Small space between icon and text
                        )
                    }
                }
            }

        }
    )
}

// Function to calculate and format a user's total score from CSV data
fun getTotalScore(context: Context, userId: String): String {
    return try {
        // Open the CSV file from app assets
        context.assets.open("data.csv").use { inputStream ->
            // Create a buffered reader to process the file
            val reader = BufferedReader(InputStreamReader(inputStream))
            // Read all lines from the CSV file into memory
            val lines = reader.readLines()

            // Find the row matching the specified user ID
            val userRow = lines.find {
                // Split each line by commas and check if column 1 (index 1) matches userId
                it.split(",").getOrNull(1)?.trim() == userId.trim()
            } ?: return "N/A"  // Return "N/A" if user not found

            // Split the matching row into individual columns
            val columns = userRow.split(",")
            // Check if user is male (case-insensitive comparison)
            val isMale = columns.getOrNull(2)?.equals("male", true) ?: false

            // Get total score from different columns based on gender
            // Male scores are in column 3 (index 3), female in column 4 (index 4)
            val totalScore = if (isMale) columns[3].toFloat() else columns[4].toFloat()

            // Format the score as a string with one decimal point (e.g., "85.5/100")
            "%.1f/100".format(totalScore)
        }
    } catch (e: Exception) {
        // Print stack trace for debugging if any error occurs
        e.printStackTrace()
        // Return "N/A" as fallback value
        "N/A"
    }
}