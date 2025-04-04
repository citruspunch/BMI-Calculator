package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import java.util.Locale
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

// Funcion para mostrar el formulario de entrada de datos
@Composable
fun InputForm() {
    var weight by remember { mutableStateOf("") }
    var isWeightValid by remember { mutableStateOf(true) }
    var currentTypeOfWeight by remember { mutableStateOf("Lbs") }
    var height by remember { mutableStateOf("") }
    var currentTypeOfHeight by remember { mutableStateOf("Cm") }
    var isHeightValid by remember { mutableStateOf(true) }
    var bmi by remember { mutableDoubleStateOf(0.0) }

    val weightOptions = listOf("Lbs", "Kg")
    val heightOptions = listOf("Cm", "Ft")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            modifier = Modifier
                .size(width = 300.dp, height = 120.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(width = 150.dp, height = 150.dp),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        isWeightValid = it.toDoubleOrNull() != null
                    },
                    label = { Text("Weight") }
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Box(
                modifier = Modifier.size(width = 150.dp, height = 150.dp),
                contentAlignment = Alignment.Center
            ) {
                SingleChoiceSegmentedButton(
                    options = weightOptions,
                    onChange = { currentTypeOfWeight = it })
            }
        }
        OutlinedTextField(
            value = height,
            onValueChange = {
                height = it
                isHeightValid = it.toDoubleOrNull() != null
            },
            label = { Text("Height") }
        )
        Spacer(modifier = Modifier.padding(6.dp))
        SingleChoiceSegmentedButton(
            options = heightOptions,
            onChange = { currentTypeOfHeight = it })
        Spacer(modifier = Modifier.padding(10.dp))
        ElevatedButton(
            enabled = isWeightValid && isHeightValid,
            onClick = {
                bmi = calculateBMI(
                    weight.toDouble(),
                    height.toDouble(),
                    currentTypeOfWeight,
                    currentTypeOfHeight
                )
            }) {
            Text("Calculate BMI")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        if (bmi > 0) BMIResult(bmi)
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    options: List<String>,
    onChange: (String) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onChange(options[selectedIndex])
                },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

// Funcion para calcular el BMI
fun calculateBMI(
    weight: Double,
    height: Double,
    currentTypeOfWeight: String,
    currentTypeOfHeight: String
): Double {
    val heightInMeters = when (currentTypeOfHeight) {
        "Ft" -> height * 0.3048
        else -> height / 100
    }

    val weightInKilograms = when (currentTypeOfWeight) {
        "Lbs" -> weight / 2.20462
        else -> weight
    }

    return weightInKilograms / (heightInMeters * heightInMeters)
}

// Funcion para mostrar el resultado del BMI
@Composable
fun BMIResult(bmi: Double) {
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
            description = "You are obese. You should lose weight."
        }
    }
    val bmiFormat = String.format(Locale.US, "%.2f", bmi)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 300.dp, height = 200.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Your BMI is:", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                bmiFormat,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(category, style = TextStyle(fontSize = 20.sp))
            Spacer(modifier = Modifier.padding(6.dp))
            Text(description, style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center))
        }
    }

}
