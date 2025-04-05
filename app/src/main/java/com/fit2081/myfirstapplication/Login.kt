package com.fit2081.myfirstapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
                    value = selectedUserId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("User ID") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    val isValid = userData[selectedUserId]?.equals(phoneInput) ?: false
                    if (isValid) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, FoodIntakeQuestionnaire::class.java).apply {
                            putExtra("USER_ID", selectedUserId)  // add user ID to intent
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

// Modified CSV processing function to return a map of User_ID to PhoneNumber
fun getUserDataFromCsv(context: Context, fileName: String): Map<String, String> {
    return try {
        context.assets.open(fileName).use { stream ->
            BufferedReader(InputStreamReader(stream)).useLines { lines ->
                lines.drop(1) // Skip header
                    .mapNotNull { line ->
                        val values = line.split(",")
                        if (values.size >= 2) {
                            values[0] to values[1] // PhoneNumber to User_ID mapping
                        } else {
                            null
                        }
                    }
                    .associate { (phone, userId) -> userId to phone } // Convert to User_ID -> PhoneNumber
            }
        }
    } catch (e: Exception) {
        emptyMap()
    }
}