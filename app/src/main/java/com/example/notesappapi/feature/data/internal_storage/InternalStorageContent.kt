package com.example.notesappapi.feature.data.internal_storage

import android.content.Context
import android.util.Log
import com.example.notesappapi.feature.domain.model.NoteContent
import java.io.File
import java.io.FilenameFilter

class InternalStorageContent(val context: Context) : IInternalStorageContent {


    override suspend fun createContentFile(idContent: Int): File {
        val file = File(context.filesDir, idContent.toString())
        return file
    }

    override suspend fun getContentFile(idContent: Int): File {
        val file = File(context.filesDir, idContent.toString())
        return file
    }

    override suspend fun deleteContent(listContent: List<NoteContent>) {
        listContent.forEach {
            val file=File(context.filesDir,it.id.toString())
                Log.d("1Content",file.delete().toString())
        }
    }
}
