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
    val scores = remember(userId) {
        getUserHEIFAScores(context, "data.csv", userId)
    }

    scores?.let {
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

        Column(
            Modifier.padding(16.dp)
        ) {
            maxScores.forEach { (label, max) ->
                val value = it[label] ?: 0f
                FoodScoreRow(
                    label = label.replaceFirstChar { it.uppercase() },
                    score = value,
                    maxScore = max
                )
            }
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



