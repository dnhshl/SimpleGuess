package com.example.main

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.main.ui.theme.SimpleCalc2025Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalc2025Theme {
                MyScreen()
            }
        }
    }
}


@Composable
fun MyScreen() {

    val context = LocalContext.current

    var numberToGuess by remember { mutableStateOf(Random.nextInt(1, 101)) }
    var entry by remember { mutableStateOf("") }
    var isPlaying by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }


    // Prüfen ob die Eingabe eine Zahl ist
    val isValidEntry = entry.toIntOrNull() != null


    // Variable Info Anzeige in Abhängigkeit von isPlaying
    var info = when {
        !isPlaying -> stringResource(id = R.string.info_message)
        else -> stringResource(id = R.string.error_message)
    }

    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Info Anzeige am oberen Bildschirmrand
        Text(
            text = info,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(30.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Eingabefeld für die Zahl
                IntegerInputField(value = entry, onInputChanged = { entry = it })
                // Button zur Bestätigung der Eingabe; nur aktiv wenn eine Zahl eingegeben wurde
                Button(

                    onClick = {
                        val guess = entry.toInt()
                        result = when {
                            guess < numberToGuess -> context.getString(R.string.too_low)
                            guess > numberToGuess -> context.getString(R.string.too_high)
                            else -> context.getString(R.string.correct)
                        }
                        isPlaying = (guess != numberToGuess)
                        isCorrect = (guess == numberToGuess)
                        counter++
                    },
                    enabled = isValidEntry
                ) {
                    Text(text = stringResource(id = R.string.guess_number))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            // Button zum Starten des Spiels
            Button(
                onClick = {
                    numberToGuess = Random.nextInt(1, 101)
                    isPlaying = true
                    result = ""
                    counter = 0
                    isCorrect = false
                },
            ) {
                Text(text = stringResource(id = R.string.start_game))
            }
            Spacer(modifier = Modifier.height(30.dp))
            // Anzeige des aktuellen Ergebnisses
            Text(
                text = result,
                style = MaterialTheme.typography.headlineLarge
            )
            // Bei korrekter Eingabe Anzeige der Versuche
            if (isCorrect) {
                Text(
                    text = stringResource(id = R.string.attempts, counter),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}