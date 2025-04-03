package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text("BMI Calculator")
                        }
                    )
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InputForm()
                    }
                }
            }
        }
    }
}

@Composable
fun InputForm () {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf(0.0) }

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
        value = weight,
        onValueChange = { weight = it },
        label = { Text("Weight (lbs)") }
        )
        Spacer(modifier = Modifier.padding(6.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        ElevatedButton(onClick = { bmi = calculateBMI(weight.toDouble(), height.toDouble()) }) {
            Text("Calculate BMI")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        if (bmi > 0) BMIResult(bmi)
    }
}

fun calculateBMI (weight: Double, height: Double): Double {
    val heightInMeters = height / 100
    val weightInKilograms = weight / 2.2
    return weightInKilograms / (heightInMeters * heightInMeters)
}

@Composable
fun BMIResult (bmi: Double) {
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    when {
        bmi < 18.5 -> {
            category = "Underweight"
            description = "You are underweight. You should gain some weight."
        }
        bmi < 25 -> {
            category = "Normal"
            description = "Congratulations! You have a normal weight."
        }
        bmi < 30 -> {
            category = "Overweight"
            description = "You are overweight. You should lose some weight."
        }
        else -> {
            category = "Obese"
            description = "You are obese. You should lose some weight."
        }
    }
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Your BMI: $bmi", style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.padding(6.dp))
        Text("Category: $category", style = TextStyle(fontSize = 20.sp))
    }
}

@Preview
@Composable
fun InputFormPreview () {
    InputForm()
}