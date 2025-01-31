package com.example.main

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IntegerInputField(onInputChanged: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            if (it.isEmpty() || it.toIntOrNull() != null) {
                text = it
                onInputChanged(it)
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.width(100.dp)
    )
}