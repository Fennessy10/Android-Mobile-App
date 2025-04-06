package com.fit2081.myfirstapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Slider
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(userId) }
                ) {
                    innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        Title()
                        DisplayHEIFAScores(context, userId)
                    }
                }
            }
        }
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Insights: Food Score",
            style = TextStyle(
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun FoodScoreRow(label: String, score: Float, maxScore: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .height(40.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.width(150.dp)
        )
        Box(modifier = Modifier.weight(1f)) {
            Slider(
                value = score,
                onValueChange = {},
                valueRange = 0f..maxScore,
                enabled = false,
                modifier = Modifier
                    .width(160.dp)
                    .height(24.dp),
            )
        }
        Text(
            text = "%.1f/%.0f".format(score, maxScore),
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
fun DisplayHEIFAScores(context: Context, userId: String) {
    // Remember and fetch user scores based on userId
    // Will only recompute when userId changes
    val scores = remember(userId) {
        getUserHEIFAScores(context, "data.csv", userId)
    }

    // Only proceed if scores were successfully loaded (non-null)
    scores?.let {
        // Define maximum possible scores for each category
        val maxScores = mapOf(
            "total" to 100f,
            "vegetables" to 5f,
            "fruit" to 5f,
            "grainsCereals" to 5f,
            "wholeGrains" to 5f,
            "meatAlternatives" to 10f,
            "dairyAlternatives" to 10f,
            "sodium" to 10f,
            "alcohol" to 10f,
            "water" to 5f,
            "sugar" to 10f,
            "saturatedFat" to 5f,
            "unsaturatedFat" to 5f
        )

        // Create a vertical layout with padding
        Column(
            Modifier.padding(16.dp)
        ) {
            // Iterate through each score category
            maxScores.forEach { (label, max) ->
                // Get the user's score for this category (default to 0 if missing)
                val value = it[label] ?: 0f

                // Display a row for each score category
                FoodScoreRow(
                    // Capitalize the first letter of the label for display
                    label = label.replaceFirstChar { it.uppercase() },
                    // The user's actual score for this category
                    score = value,
                    // The maximum possible score for this category
                    maxScore = max
                )
            }
        }
    } ?: // Fallback display if scores couldn't be loaded (null case)
    Text("User not found or error loading data")
}



fun getUserHEIFAScores(context: Context, fileName: String, userId: String): Map<String, Float>? {
    return try {
        // Open and read the CSV file from assets
        context.assets.open(fileName).use { inputStream ->
            // Create a buffered reader for the input stream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines = reader.readLines()

            // Extract column headers from first line
            val header = lines.first().split(",")
            // Find the row matching the requested userId (checking column index 1)
            val userRow = lines.find { it.split(",").getOrNull(1)?.trim() == userId.trim() }
                ?: return null  // Return null if user not found

            // Split the user's row into individual columns
            val columns = userRow.split(",")
            // Get gender from column index 2
            val sex = columns.getOrNull(2) ?: return null
            // Determine if user is male (case-insensitive check)
            val isMale = sex.equals("male", ignoreCase = true)

            // Helper function to get score for a specific category
            fun getScore(prefix: String): Float {
                // Determine gender-specific column suffix
                val suffix = if (isMale) "Male" else "Female"
                // Construct full column name (e.g., "VegetablesHEIFAscoreMale")
                val columnName = "${prefix}HEIFAscore$suffix"
                // Find index of the column in header row
                val index = header.indexOf(columnName)
                // Get and parse the score value (default to 0 if missing/invalid)
                return columns.getOrNull(index)?.toFloatOrNull() ?: 0f
            }

            // Create map of all scores for this user
            mapOf(
                // Get total score (special case with different column naming)
                "total" to (columns[header.indexOf(if (isMale) "HEIFAtotalscoreMale" else "HEIFAtotalscoreFemale")]
                    // converts to float if somehow not float thereby dodging error
                    .toFloatOrNull() ?: 0f),
                // Get all category-specific scores using helper function
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
        // Print stack trace for debugging if any error occurs
        e.printStackTrace()
        // Return null to indicate failure
        null
    }
}


