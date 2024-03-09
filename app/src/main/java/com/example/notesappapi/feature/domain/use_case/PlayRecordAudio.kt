package com.example.notesappapi.feature.domain.use_case

import android.util.Log
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent
import com.example.notesappapi.feature.domain.util.PlayerAudioProvider

class PlayRecordAudio(
    val playerAudioProvider: PlayerAudioProvider,
    val apiRepositoryContent: ApiRepositoryContent
) {
    suspend fun Play(idContent:Int,stopCallBack: () -> Unit) {
        Stop()
        val file = apiRepositoryContent.getContentFile(idContent)
        Log.d("bbbb",file.exists().toString())
        playerAudioProvider.play(file, stopCallBack)
    }
    fun Stop(){
        playerAudioProvider.stop()
    }
}