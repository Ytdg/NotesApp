package com.example.notesappapi.feature.domain.util.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.notesappapi.feature.domain.util.PlayerAudioProvider
import java.io.File

class PlayerAudio(val context: Context) : PlayerAudioProvider {
    private var mediaPlayer: MediaPlayer? = null

    override fun play(audioFile: File,stopCallBack:()->Unit) {
        mediaPlayer = MediaPlayer.create(context, audioFile.toUri())
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {stopCallBack()}
    }

    override fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}