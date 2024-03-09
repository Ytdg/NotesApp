package com.example.notesappapi.feature.domain.util

import java.io.File

interface PlayerAudioProvider{
    fun play(audioFile:File,stopCallBack:()->Unit)
    fun stop()
}