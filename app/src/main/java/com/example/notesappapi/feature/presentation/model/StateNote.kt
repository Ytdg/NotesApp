package com.example.notesappapi.feature.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notesappapi.feature.domain.model.ConstColorNote
import com.example.notesappapi.feature.domain.model.Data
import com.example.notesappapi.feature.domain.model.NoteContent

data class StateNote(
    val color: Color = ConstColorNote.defaultColorNote,
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue=TextFieldValue(),

)
