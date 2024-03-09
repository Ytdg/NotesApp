package com.example.notesappapi.feature.domain.util

import java.io.File

interface RecorderAudioProvider{
    fun startRecordAudio(outPutFile: File)
    fun stopRecordAudio():File
}