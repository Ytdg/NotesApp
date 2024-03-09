package com.example.notesappapi.feature.domain.repository

import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.model.NoteContent
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getNotes(): Flow<List<Note>>


    suspend fun DeleteNote(id: Int)
    suspend fun DeleteContent(listContent: List<NoteContent>)
    suspend fun CreateContent(noteContent: NoteContent): Long
    suspend fun CreateNote(note: Note): Long

    suspend fun saveNoteWithContent(note: Note, listContent: List<NoteContent>)

    suspend fun GetNote(id: Int): Note

    suspend fun GetContent(id: Int): List<NoteContent>
}