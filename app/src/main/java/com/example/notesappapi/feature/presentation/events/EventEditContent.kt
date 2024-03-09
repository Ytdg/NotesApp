package com.example.notesappapi.feature.presentation.events

import android.net.Uri
import com.example.notesappapi.feature.presentation.model.StateNoteContent

sealed class EventEditContent {
    class StartRecording():EventEditContent()
    class StopRecording():EventEditContent()
    class PlaySong(val  stateContent: StateNoteContent):EventEditContent()
    class  StopSong():EventEditContent()
    class DeleteContent(val stateContent: StateNoteContent):EventEditContent()
    class PutPhoto(list: List<Uri>):EventEditContent()
}