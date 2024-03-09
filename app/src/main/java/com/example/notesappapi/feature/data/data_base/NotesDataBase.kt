package com.example.notesappapi.feature.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.model.NoteContent

@Database(
    entities = [Note::class,NoteContent::class],
    version = 1
)
abstract class NotesDataBase : RoomDatabase() {
    abstract val notesDao: NotesDao
}