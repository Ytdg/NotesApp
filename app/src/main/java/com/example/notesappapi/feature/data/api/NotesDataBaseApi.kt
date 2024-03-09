package com.example.notesappapi.feature.data.api

import android.content.Context
import android.util.Log
import com.example.notesappapi.feature.data.data_base.NotesDao
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow

class NotesDataBaseApi(val notesDao: NotesDao) : ApiRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
    }

    override suspend fun DeleteNote(id: Int) {
        notesDao.Delete(id)
    }

    override suspend fun DeleteContent(listContent: List<NoteContent>) {
        notesDao.DeleteContent(listContent)
    }

    override suspend fun CreateContent(noteContent: NoteContent): Long {
        return notesDao.CreateContent(noteContent)
    }

    override suspend fun CreateNote(note: Note): Long {
        return notesDao.CreateNote(note)
    }

    override suspend fun saveNoteWithContent(note: Note, listContent: List<NoteContent>) {
        notesDao.saveNoteWithContent(note, listContent)
    }

    override suspend fun GetNote(id: Int): Note {
        return notesDao.GetNote(id)
    }

    override suspend fun GetContent(id: Int): List<NoteContent> {
        return notesDao.GetContent(id)
    }


}