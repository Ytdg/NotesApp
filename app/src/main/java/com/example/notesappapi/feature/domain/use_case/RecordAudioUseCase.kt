package com.example.notesappapi.feature.domain.use_case

import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent
import com.example.notesappapi.feature.domain.util.RecorderAudioProvider
import java.io.File

class RecordAudioUseCase(
    val recorderAudioProvider: RecorderAudioProvider,
    val apiRepositoryContent: ApiRepositoryContent
) {
    suspend fun startRecord(idContent:Int){
        val file = apiRepositoryContent.createContentFile(idContent)
        recorderAudioProvider.startRecordAudio(file)
    }
    fun stopRecord(): File{
        val file = recorderAudioProvider.stopRecordAudio()
        return file
    }
    fun getDurationAudioFile(file: File): String {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(file.absolutePath)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.let { Integer.parseInt(it) }
        retriever.release()
        if (duration != null) {
            return convertSecond(duration.toLong())
        } else return "0"
    }
    private fun convertSecond(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "$minutes:$seconds"
    }
}