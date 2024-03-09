package com.example.notesappapi.feature.presentation.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappapi.NavDestinationArgumentsDefaultValue
import com.example.notesappapi.feature.domain.model.Data
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.use_case.UseCases
import com.example.notesappapi.feature.presentation.events.EventEditNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelNote @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle,
    private val iSharedDataServiseVm: ISharedDataServiseVm
) : ViewModel() {
    private val _stateNote: MutableStateFlow<StateNote> = MutableStateFlow(StateNote())
    val stateNote = _stateNote.asStateFlow()

    var dateCreatingNote: String = Data.currentDate
        private set

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            val idNote = savedStateHandle.get<Int>("id")!!
            if (idNote != NavDestinationArgumentsDefaultValue.passId) {
                iSharedDataServiseVm.idNote=idNote
                val note = useCases.getNoteUseCase(idNote)
                _stateNote.value = StateNote(
                    color = Color(note.color),
                    title = TextFieldValue(note.title),
                    description = TextFieldValue(note.description)
                )
            } else {
                createNote()
            }
        }
    }

    fun onEvent(event: EventEditNote) {
        when (event) {
            is EventEditNote.SelectColor -> {
                _stateNote.value = _stateNote.value.copy(color = event.color)
            }

            is EventEditNote.EditTitle -> {
                _stateNote.value = _stateNote.value.copy(title = event.textFieldValue)
            }

            is EventEditNote.EditDescription -> {
                _stateNote.value = _stateNote.value.copy(description = event.textFieldValue)
            }
            is EventEditNote.SaveNote -> {
                viewModelScope.launch(context = Dispatchers.IO) {
                    useCases.saveEditNoteUseCase(
                        note = Note(
                            title = _stateNote.value.title.text,
                            color = _stateNote.value.color.toArgb(),
                            description = _stateNote.value.description.text,
                            id = iSharedDataServiseVm.idNote
                        ),
                        noteContent = event.list
                    )
                }
            }

        }
    }
    private suspend fun createNote() {
        iSharedDataServiseVm.idNote = useCases.createNoteUseCase(
            Note(
                title = "Новая заметка",
                color = _stateNote.value.color.toArgb(),
                description = _stateNote.value.description.text
            )
        )
    }
}