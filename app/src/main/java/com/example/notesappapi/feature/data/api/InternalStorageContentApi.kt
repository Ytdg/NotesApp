package com.example.notesappapi.feature.data.api

import com.example.notesappapi.feature.data.internal_storage.IInternalStorageContent
import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent
import java.io.File

class InternalStorageContentApi(val iinternalStorageContent: IInternalStorageContent) :
    ApiRepositoryContent {

    override suspend fun createContentFile(idContent: Int): File {
        return  iinternalStorageContent.createContentFile(idContent)
    }

    override suspend fun getContentFile(idContent: Int): File {
        return iinternalStorageContent.getContentFile(idContent)
    }

    override suspend fun deleteContent(listContent: List<NoteContent>) {
       iinternalStorageContent.deleteContent(listContent)
    }

}