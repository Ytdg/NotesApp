package com.example.notesappapi.feature.presentation.events

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notesappapi.feature.domain.model.NoteContent

sealed class EventEditNote{
    data class  SelectColor(val color: Color):EventEditNote()
    data class EditTitle(val textFieldValue: TextFieldValue):EventEditNote()
    data class EditDescription(val textFieldValue: TextFieldValue):EventEditNote()
    class  SaveNote(val list: List<NoteContent>):EventEditNote()
}