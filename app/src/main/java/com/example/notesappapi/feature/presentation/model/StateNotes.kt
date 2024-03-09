package com.example.notesappapi.feature.presentation.model

import android.icu.text.StringSearch
import com.example.notesappapi.feature.domain.model.Note

data class StateNotes(
    val listNotes:List<Note> = listOf(),
    val listNotesSearch: List<Note> = listOf(),
)