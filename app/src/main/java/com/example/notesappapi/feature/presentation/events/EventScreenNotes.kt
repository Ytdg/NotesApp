package com.example.notesappapi.feature.presentation.events

import com.example.notesappapi.feature.domain.model.Note

sealed class EventScreenNotes {
    data class Search(val key: String?) : EventScreenNotes()
    data class DeleteNote(val note: Note):EventScreenNotes()
}