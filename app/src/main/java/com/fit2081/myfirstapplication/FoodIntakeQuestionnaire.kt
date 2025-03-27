package com.fit2081.myfirstapplication

import android.app.TimePickerDialog
import android.widget.TimePicker
import java.util.Calendar
import android.content.Context
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.unit.sp
import com.fit2081.myfirstapplication.ui.theme.MyFirstApplicationTheme



class FoodIntakeQuestionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        TopAppBar()
                    }
                }
            }
        }
    }
}

//@Composable
//fun Title(){
//    Text(
//        "Food Intake Questionnaire",
//        style = TextStyle(
//            fontSize = (20.sp),
//            fontWeight = (FontWeight.Bold)
//        )
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    // Create a scroll state for the content
    val scrollState = rememberScrollState()

    // Create scroll behavior for the app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Back button handler
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher




    // checkbox food composable variables
    val categories = listOf(
        "Fruits", "Vegetables", "Grains", "Red Meat", "Seafood",
        "Poultry", "Fish", "Eggs", "Nuts/Seeds"
    )
    var checkedStates by remember { mutableStateOf(List(categories.size) { false }) }


    // persona dropdown variables
    var selectedPersona by remember { mutableStateOf("health devotee") }


    // time composable variables
    var biggestMealTimeResponse by remember { mutableStateOf("Click to Select Time") }
    var bedTimeResponse by remember { mutableStateOf("Click to Select Time") }
    var wakeTimeResponse by remember { mutableStateOf("Click to Select Time") }






    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Food Intake Questionnaire",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedDispatcher?.onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FoodCategories(checkedStates, onCheckListChange = { newList -> checkedStates = newList })
            PersonaView()
            PersonaDropdown(selectedOption = selectedPersona, onPersonaSelected = { newPersona -> selectedPersona = newPersona
                }
            )
            Timings("What time of day do you normally eat your biggest meal?", biggestMealTimeResponse, onTimeSelected = { biggestMealTimeResponse = it })
            Timings("What time of day do you go to sleep at night?", bedTimeResponse, onTimeSelected = { bedTimeResponse = it })
            Timings("What time of day do you wake up in the morning?", wakeTimeResponse, onTimeSelected = { wakeTimeResponse = it })
            Spacer(modifier = Modifier.height(8.dp))
            SaveButton()
        }
    }
}


@Composable
fun FoodCategories(
    checkedStates: List<Boolean>,
    onCheckListChange: (List<Boolean>) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Tick the food categories you can eat",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // Helper function to update specific index
        fun updateState(index: Int, checked: Boolean) {
            val newList = checkedStates.toMutableList().apply {
                this[index] = checked
            }
            onCheckListChange(newList)
        }

        // Row 1: Fruits (0), Vegetables (1)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedStates[0],
                onCheckedChange = { updateState(0, it) }
            )
            Text("Fruits")
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checkedStates[1],
                onCheckedChange = { updateState(1, it) }
            )
            Text("Vegetables")
        }

        // Row 2: Red Meat (3), Seafood (4)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedStates[3],
                onCheckedChange = { updateState(3, it) }
            )
            Text("Red Meat")
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checkedStates[4],
                onCheckedChange = { updateState(4, it) }
            )
            Text("Seafood")
        }

        // Row 3: Fish (6), Eggs (7), Nuts/Seeds (8)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedStates[6],
                onCheckedChange = { updateState(6, it) }
            )
            Text("Fish")
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checkedStates[7],
                onCheckedChange = { updateState(7, it) }
            )
            Text("Eggs")
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checkedStates[8],
                onCheckedChange = { updateState(8, it) }
            )
            Text("Nuts/Seeds")
        }

        // Row 4: Grains (2), Poultry (5)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedStates[2],
                onCheckedChange = { updateState(2, it) }
            )
            Text("Grains")
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checkedStates[5],
                onCheckedChange = { updateState(5, it) }
            )
            Text("Poultry")
        }
    }
}



@Composable
fun PersonaView() {
    var selectedPersona by remember { mutableStateOf<String?>(null) }
    var selectedDescription by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Your Persona",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))

        Row {
            PersonaButton("Health Devotee", buttonColors) {
                selectedPersona = "Health Devotee"
                selectedDescription =
                    "I'm passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy."
                showDialog = true
            }
            Spacer(modifier = Modifier.width(8.dp))
            PersonaButton("Mindful Eater", buttonColors) {
                selectedPersona = "Mindful Eater"
                selectedDescription =
                    "I pay close attention to what I eat and try to make balanced, nutritious choices. I am mindful of portion sizes and avoid overeating."
                showDialog = true
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row {
            PersonaButton("Wellness Striver", buttonColors) {
                selectedPersona = "Wellness Striver"
                selectedDescription =
                    "I am on a journey to improve my eating habits. I try to eat well, but I also allow myself indulgences without guilt."
                showDialog = true
            }
            Spacer(modifier = Modifier.width(8.dp))
            PersonaButton("Balance Seeker", buttonColors) {
                selectedPersona = "Balance Seeker"
                selectedDescription =
                    "I believe in a balanced approach to eating. I enjoy all kinds of food but make sure I don’t overdo anything."
                showDialog = true
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row {
            PersonaButton("Health Procrastinator", buttonColors) {
                selectedPersona = "Health Procrastinator"
                selectedDescription =
                    "I want to eat healthier but often find myself putting it off. I may have some knowledge about good nutrition, but I don’t always apply it."
                showDialog = true
            }
            Spacer(modifier = Modifier.width(8.dp))
            PersonaButton("Food Carefree", buttonColors) {
                selectedPersona = "Food Carefree"
                selectedDescription =
                    "I eat whatever I feel like without much concern for health aspects. I enjoy food for its taste and variety."
                showDialog = true
            }
        }
    }

    if (showDialog && selectedPersona != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = getPersonaImageResource(selectedPersona)),
                        contentDescription = "Persona Image",
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(selectedPersona!!, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = { Text(selectedDescription ?: "") },
            confirmButton = {
                Button(onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))) {
                    Text("Dismiss", color = androidx.compose.ui.graphics.Color.White)
                }
            }
        )
    }
}

@Composable
fun PersonaButton(label: String, buttonColors: ButtonColors, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(label, color = androidx.compose.ui.graphics.Color.White)
    }
}

fun getPersonaImageResource(persona: String?): Int {
    return when (persona) {
        "Health Devotee" -> R.drawable.buddahicon
        "Mindful Eater" -> R.drawable.mindfulicon
        "Wellness Striver" -> R.drawable.wellnessicon
        "Balance Seeker" -> R.drawable.balanceicon
        "Health Procrastinator" -> R.drawable.procrastinateicon
        "Food Carefree" -> R.drawable.carefreeicon
        else -> R.drawable.nutritracklogo // Fallback image
    }
}

@Composable
fun PersonaDropdown(selectedOption: String, onPersonaSelected: (String) -> Unit) {
    val options = listOf("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker", "Health Procrastinator", "Food Carefree")
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Select your persona:")

        Box {
            Text(
                text = selectedOption.ifEmpty { "Click to select an option" },
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onPersonaSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Timings(question: String, timeText: String, onTimeSelected: (String) -> Unit) { //uses a lambda to allow the variable to be updated even outside of this function when changed
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            onTimeSelected(String.format("%02d:%02d", hourOfDay, minute)) // Call function to update state
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = question,
            modifier = Modifier.weight(1f) //wraps text
        )
        Text(
            text = timeText,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { timePickerDialog.show() }
                .padding(16.dp)
        )
    }
}

@Composable
fun SaveButton(){

    val context = LocalContext.current

    Button(
        onClick = {
            val sharedPref = context.getSharedPreferences("SavedLOL", Context.MODE_PRIVATE).edit()

            sharedPref.putString("biggest meal", timeText)// biggest meal answer
        },
    ) {
        Text("Save Values")
    }
}