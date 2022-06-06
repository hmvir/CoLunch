package com.example.colunch.widgets

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.example.colunch.models.addToFirestore

@Composable
fun SimpleTextField() : String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }
    )
    return text.text
}

@Composable
fun SimpleButton(inputtext : String) {
    Button(onClick = {
        addToFirestore(inputtext)
    }) {
        Text(text = "Simple Button")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}