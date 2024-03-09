package com.example.notesappapi.feature.domain.util.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.notesappapi.feature.domain.util.RecorderAudioProvider
import java.io.File

class RecorderAudio(val context: Context) : RecorderAudioProvider {

    private var mediaRecorder: MediaRecorder? = null
    private var outPutFileAudio: File? = null

    private fun createMediaRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun startRecordAudio(outPutFile: File) {
        Log.d("bbbb","uuuu")
        createMediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
            setOutputFile(outPutFile)
            prepare()
            start()
            mediaRecorder = this
            outPutFileAudio = outPutFile
        }

    }

    override fun stopRecordAudio(): File {

        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder = null
        return outPutFileAudio!!
    }
}