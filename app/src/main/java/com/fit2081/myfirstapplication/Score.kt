package com.fit2081.myfirstapplication

import android.app.TimePickerDialog
import android.widget.TimePicker
import java.util.Calendar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fit2081.myfirstapplication.ui.theme.MyFirstApplicationTheme
import java.io.BufferedReader
import java.io.InputStreamReader

class Score : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the user ID from the intent
        val userId = intent.getStringExtra("USER_ID") ?: ""

        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {

                val context = LocalContext.current

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        Title()
                        MainScores(userId)
                        DisplayHEIFAScores(context, userId)
                        BottomBar(userId)

                    }
                }
            }
        }
    }
}

@Composable
fun Title(){
    Text(
        "Insights: Food Score",
        style = TextStyle(
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun MainScores(userId: String){
    Column {
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Fruits", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)
//        FoodRow("Vegetables", remember { mutableFloatStateOf(10f) }, 5f)


        Row {
            Text(
                text = "Fruits",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Grains & Cereals",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Whole Grains",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Meat & Alternatives",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Dairy",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Water",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Unsaturated Fats",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Sodium",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Sugar",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Sugar",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Alcohol",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
        Row {
            Text(
                text = "Discretionary Foods",
                style = TextStyle(
                    fontSize = 30.sp,
                )
            )
        }
    }
}

@Composable
fun FoodRow(food: String, sliderValue: MutableState<Float>, sliderMaxRange: Float) {
    Row {
        Text(
            text = food,
            style = TextStyle(
                fontSize = 20.sp,
            )
        )
        Slider(
            value = sliderValue.value,
            onValueChange = { sliderValue.value = it },
            valueRange = 0f..sliderMaxRange,
            enabled = false  // this makes the slider non-interactive
        )
        Text(
            text = "${sliderValue.value}/${sliderMaxRange}",
            style = TextStyle(
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun DisplayHEIFAScores(context: Context, userId: String) {
    val scores = remember(userId) {
        getUserHEIFAScores(context, "data.csv", userId)
    }

    scores?.let {
        Column {
            Text("Total HEIFA Score: ${it["total"]}")
            Text("Vegetables: ${it["vegetables"]}")
            Text("Fruit: ${it["fruit"]}")
            Text("Grains & Cereals: ${it["grainsCereals"]}")
            // Add other score displays as needed...
        }
    } ?: Text("User not found or error loading data")
}

fun getUserHEIFAScores(context: Context, fileName: String, userId: String): Map<String, Float>? {
    return try {
        context.assets.open(fileName).use { inputStream ->
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines = reader.readLines()

            val header = lines.first().split(",")
            val userRow = lines.find { it.split(",").getOrNull(1)?.trim() == userId.trim() }
                ?: return null

            val columns = userRow.split(",")
            val sex = columns.getOrNull(2) ?: return null
            val isMale = sex.equals("male", ignoreCase = true)

            fun getScore(prefix: String): Float {
                val suffix = if (isMale) "Male" else "Female"
                val columnName = "${prefix}HEIFAscore$suffix"
                val index = header.indexOf(columnName)
                return columns.getOrNull(index)?.toFloatOrNull() ?: 0f
            }

            mapOf(
                "total" to (columns[header.indexOf(if (isMale) "HEIFAtotalscoreMale" else "HEIFAtotalscoreFemale")]
                    .toFloatOrNull() ?: 0f),
                "vegetables" to getScore("Vegetables"),
                "fruit" to getScore("Fruit"),
                "grainsCereals" to getScore("Grainsandcereals"),
                "wholeGrains" to getScore("Wholegrains"),
                "meatAlternatives" to getScore("Meatandalternatives"),
                "dairyAlternatives" to getScore("Dairyandalternatives"),
                "sodium" to getScore("Sodium"),
                "alcohol" to getScore("Alcohol"),
                "water" to getScore("Water"),
                "sugar" to getScore("Sugar"),
                "saturatedFat" to getScore("SaturatedFat"),
                "unsaturatedFat" to getScore("UnsaturatedFat")
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//// Composable function for the main screen of the CSV processor.
//fun CSVProcessorScreen(context: Context, modifier: Modifier = Modifier) {
//    // State to hold the location input.
//    var location by remember { mutableStateOf("") }
//    // State to hold the count of rows matching the location.
//    var count by remember { mutableStateOf(0) }
//    // State to hold the text to display the result.
//    var resultText by remember { mutableStateOf("Result: 0") }
//    // Column to arrange UI elements vertically.
//    Column(
//        modifier = modifier
//            .padding(16.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Text field for entering the location.
//        TextField(
//            value = location,
//            // Update location state when the text changes.
//            onValueChange = { location = it },
//            label = { Text("Enter Location") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        )
//        // Spacer for adding vertical space.
//        Spacer(modifier = Modifier.height(16.dp))
//        // Button to process the CSV.
//        Button(
//            onClick = {
//                // Call the function to count rows matching the location.
//                // the second parameter is the file name that should be saved in
//                // app/src/main/assets/data.csv
//                count = countRowsByLocation(context, "data.csv", location)
//                // Update the result text to show the count.
//                resultText = "Result: $count"
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        ) {
//            Text("Process CSV")
//        }
//        // Spacer for adding vertical space.
//        Spacer(modifier = Modifier.height(16.dp))
//        // Text to display the result.
//        Text(text = resultText)
//    }
//}
//
//// Function to count the number of rows in a CSV file that match a given location.
//fun countRowsByLocation(context: Context, fileName: String, location: String): Int {
//    var count = 0 // Initialize the count to 0.
//    var assets = context.assets // Get the asset manager.
//    // Try to open the CSV file and read it line by line.
//    try {
//        val inputStream = assets.open(fileName) // Open the file from assets.
//        // Create a buffered reader for efficient reading.
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.useLines { lines ->
//            lines.drop(1).forEach { line -> // Skip the header row.
//                val values = line.split(",") // Split each line into values.
//                // Check if the row has enough columns and
//                // if the 7th column matches the location.
//                if (values.size > 6 && values[7].trim() == location.trim()) {
//                    count++ // Increment the count if the location matches.
//                }
//            }
//        }
//    } catch (e: Exception) {
//        // Handle any exceptions that might occur during file reading.
//    }
//    // Return the total count of rows matching the location.
//    return count
//}




