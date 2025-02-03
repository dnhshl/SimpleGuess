package com.example.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.main.ui.theme.SimpleCalc2025Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalc2025Theme {
                MyScreen(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun MyScreen(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()



    // Prüfen ob die Eingabe eine Zahl ist
    val isValidEntry = uiState.entry.toIntOrNull() != null


    // Variable Info Anzeige in Abhängigkeit von isPlaying
    var info = when {
        !uiState.isPlaying -> stringResource(id = R.string.info_message)
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
                IntegerInputField(value = uiState.entry, onInputChanged = { viewModel.onEntryChanged(it) })
                // Button zur Bestätigung der Eingabe; nur aktiv wenn eine Zahl eingegeben wurde
                Button(
                    onClick = { viewModel.onClickGuessNumberButton() },
                    enabled = isValidEntry
                ) {
                    Text(text = stringResource(id = R.string.guess_number))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            // Button zum Starten des Spiels
            Button(
                onClick = { viewModel.onClickStartGameButton() },
                ) {
                Text(text = stringResource(id = R.string.start_game))
            }
            Spacer(modifier = Modifier.height(30.dp))
            // Anzeige des aktuellen Ergebnisses
            Text(
                text = uiState.result,
                style = MaterialTheme.typography.headlineLarge
            )
            // Bei korrekter Eingabe Anzeige der Versuche
            if (uiState.isCorrect) {
                Text(
                    text = stringResource(id = R.string.attempts, uiState.counter),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

