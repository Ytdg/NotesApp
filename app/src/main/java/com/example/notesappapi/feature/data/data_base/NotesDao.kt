package com.example.notesappapi.feature.data.data_base

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.model.NoteContent
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM Note ORDER by timeSave DESC")
    fun getNotes(): Flow<List<Note>>


    @Transaction
    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun Delete(id: Int)

    @Delete
    suspend fun DeleteContent(list:List<NoteContent>)

    @Upsert
    suspend fun CreateNote(note: Note): Long

    @Upsert
    suspend fun CreateContent(noteContent: NoteContent): Long

    @Upsert
    @Transaction
    suspend fun saveNoteWithContent(note: Note, listContent: List<NoteContent>)

    @Query("SELECT * FROM Note WHERE id =:id")
    suspend fun GetNote(id: Int): Note

    @Query("SELECT * FROM NoteContent WHERE  parentId=:parentId")
    suspend fun GetContent(parentId: Int): List<NoteContent>


}