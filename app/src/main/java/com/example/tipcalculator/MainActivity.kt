package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Layout()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    val amountInput = remember { mutableStateOf("") }
    val tipInput = remember { mutableStateOf("") }
    val roundUp = remember { mutableStateOf(false) }

    // Safely parse user inputs
    val amount = amountInput.value.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.value.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent, roundUp.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Calculate Tip",
            modifier = Modifier.padding(start = 12.dp)
        )

        // Bill Amount Input Field
        TextField(
            value = amountInput.value,
            onValueChange = { amountInput.value = it },
            label = { Text("Bill Amount") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = "Money Image",
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .background(Color.Gray))

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Percentage Input Field
        TextField(
            value = tipInput.value,
            onValueChange = { tipInput.value = it },
            label = { Text("Tip Percentage") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.percent),
                    contentDescription = "Percent Image",
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Round Up Tip Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round Up Tip?")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = roundUp.value,
                onCheckedChange = { roundUp.value = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Amount Display
        Text(
            text = "Tip Amount: $tip",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewLayout() {
    Layout()
}
