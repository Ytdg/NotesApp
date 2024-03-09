package com.example.notesappapi.feature.presentation.model

import android.net.Uri
import com.example.notesappapi.feature.domain.model.TypeContent


data class StateNoteContent(
    val text:String?=null,
    val typeContent: TypeContent,
    val duration: String?=null,
    val isActive:Boolean=false,
    val idContent:Int,
    val uri: Uri?=null
)
data class  StateAudioPlayer(
    val idContent: Int? = null
)
