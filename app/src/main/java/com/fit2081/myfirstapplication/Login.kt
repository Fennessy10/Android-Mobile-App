package com.fit2081.myfirstapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fit2081.myfirstapplication.ui.theme.MyFirstApplicationTheme
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import java.io.BufferedReader
import java.io.InputStreamReader


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        LoginScreen()
                    }

                }
            }
        }
    }

}

@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var phoneInput by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedUserId by remember { mutableStateOf("") }

    // Load both user IDs and their corresponding phone numbers
    val userData = remember { getUserDataFromCsv(context, "data.csv") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.nutritracklogo),
                contentDescription = "Fit logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // User ID Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(

                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    value = selectedUserId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("User ID") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, //Adds a standard dropdown arrow icon at the right end of the TextField
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userData.keys.forEach { userId ->
                        DropdownMenuItem(
                            text = { Text(userId) },
                            onClick = {
                                selectedUserId = userId
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Field
            TextField(
                value = phoneInput,
                onValueChange = { phoneInput = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // adds a keyboard
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    val isValid = userData[selectedUserId]?.equals(phoneInput) ?: false
                    if (isValid) {
                        // Check if questionnaire was completed
                        val userPrefs = context.getSharedPreferences("QuestionnaireAnswers$selectedUserId", Context.MODE_PRIVATE)
                        val isQuestionnaireCompleted = userPrefs.getBoolean("questionnaire_completed", false)

                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Navigate to Home if completed, else Questionnaire
                        val destination = if (isQuestionnaireCompleted) {
                            Home::class.java
                        } else {
                            FoodIntakeQuestionnaire::class.java
                        }

                        val intent = Intent(context, destination).apply {
                            putExtra("USER_ID", selectedUserId)
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Login")
            }
        }
    }
}

// Function to read user data from a CSV file and return a mapping of User_ID to PhoneNumber
fun getUserDataFromCsv(context: Context, fileName: String): Map<String, String> {
    return try {
        // Open the CSV file from the app's assets folder
        context.assets.open(fileName).use { stream ->
            // Create a BufferedReader to read the file line by line
            BufferedReader(InputStreamReader(stream)).useLines { lines ->
                // Process each line of the CSV file
                lines.drop(1) // Skip the first line (header row) of the CSV file
                    .mapNotNull { line ->
                        // Split each line by commas to get individual values
                        val values = line.split(",")
                        // Check if the line has at least 2 values (PhoneNumber and User_ID)
                        if (values.size >= 2) {
                            // Return a Pair of (PhoneNumber, User_ID) if valid
                            values[0] to values[1] // Pair format: (PhoneNumber, User_ID)
                        } else {
                            // Skip malformed lines by returning null
                            null
                        }
                    }
                    // Convert the list of Pairs into a Map where:
                    // Key = User_ID (values[1]), Value = PhoneNumber (values[0])
                    .associate { (phone, userId) -> userId to phone }
            }
        }
    } catch (e: Exception) {
        // Return an empty map if any error occurs (file not found, parsing error, etc.)
        emptyMap()
    }
}