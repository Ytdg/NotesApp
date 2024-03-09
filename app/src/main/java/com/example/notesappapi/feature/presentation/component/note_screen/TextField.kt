package com.example.notesappapi.feature.presentation.component.note_screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TextField(
    modifier: Modifier,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle,
    textFieldColors: TextFieldColors,
    placeholder:@Composable() (() -> Unit)
) {
    val iterationSourse = remember {
        MutableInteractionSource()
    }
    BasicTextField(
        modifier=modifier,
        value = textFieldValue,
        onValueChange = onValueChange,
        interactionSource = iterationSourse,
        textStyle = textStyle,
        cursorBrush =SolidColor(Color(169, 144, 221))
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = textFieldValue.text,
            innerTextField = { innerTextField() },
            enabled = false,
            singleLine = false,
            visualTransformation = VisualTransformation.None,
            interactionSource = iterationSourse,
            colors = textFieldColors,
            placeholder =placeholder,
            contentPadding = PaddingValues(0.dp)
        )
    }


}