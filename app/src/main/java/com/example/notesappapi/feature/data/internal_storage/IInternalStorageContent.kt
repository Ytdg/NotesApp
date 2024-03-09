package com.example.notesappapi.feature.data.internal_storage

import com.example.notesappapi.feature.domain.model.NoteContent
import java.io.File

interface IInternalStorageContent{
    suspend fun createContentFile(idContent:Int):File
    suspend fun  getContentFile(idContent:Int):File
    suspend fun deleteContent(listContent:List<NoteContent>)
}