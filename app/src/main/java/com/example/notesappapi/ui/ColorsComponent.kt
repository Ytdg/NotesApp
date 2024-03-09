package com.example.notesappapi.ui

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TextFieldColors()=TextFieldDefaults.colors(
    disabledContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
)