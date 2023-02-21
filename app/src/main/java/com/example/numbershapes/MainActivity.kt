package com.example.numbershapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbershapes.ui.theme.NumberShapesTheme
import kotlin.math.floor
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberShapesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NumberScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val text = remember {
            mutableStateOf("")
        }

        val hint = remember {
            mutableStateOf("")
        }

        Text(
            text = stringResource(R.string.number_shapes),
            fontSize = 50.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.hint),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = hint.value,
            color = Color.Blue,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (text.value.isBlank()) {
                    hint.value = "Please enter a number in above text field."
                } else {
                    Number(text.value.toInt()).apply {
                        when {
                            isSquare && !isTriangular -> {
                                hint.value = "${this.number} is square, but not triangular."
                            }
                            isTriangular && !isSquare -> {
                                hint.value = "${this.number} is triangular, but not square."
                            }
                            isSquare && isTriangular -> {
                                hint.value = "${this.number} is triangular and square."
                            }
                        }
                    }
                }
            },
        ) {
            Text(text = "TEST NUMBER")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NumberShapesTheme {
        NumberScreen()
    }
}

class Number(val number: Int) {
    val isSquare: Boolean
        get() {
            val squareRoot = sqrt(number.toDouble())
            return squareRoot == floor(squareRoot)
        }
    val isTriangular: Boolean
        get() {
            var x = 1
            var triangularNumber = 1
            while (triangularNumber < number) {
                x++
                triangularNumber += x
            }
            return triangularNumber == number
        }
}
