package com.example.notesappapi.feature.domain.repository

import com.example.notesappapi.feature.domain.model.NoteContent
import java.io.File

interface ApiRepositoryContent{
    suspend fun createContentFile(idContent:Int):File
    suspend fun  getContentFile(idContent:Int):File
    suspend fun deleteContent(listContent:List<NoteContent>)
}
/*InternalStorageContent*/