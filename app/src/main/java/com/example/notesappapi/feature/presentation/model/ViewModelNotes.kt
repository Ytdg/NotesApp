package com.example.notesappapi.feature.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.use_case.UseCases
import com.example.notesappapi.feature.presentation.events.EventScreenNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelNotes @Inject constructor(val useCases: UseCases) : ViewModel() {
    private val _stateNotes: MutableStateFlow<StateNotes> = MutableStateFlow(StateNotes())
    val stateNotes = _stateNotes.asStateFlow()

    private var job: Job = Job()

    init {
        viewModelScope.launch {
            useCases.getNotesUseCase().collect {
                _stateNotes.value = StateNotes(listNotes = it)
            }
        }
    }

    fun OnEvent(event: EventScreenNotes) {
        job.cancel()
        when (event) {
            is EventScreenNotes.Search -> {
                job = viewModelScope.launch {
                    _stateNotes.update {
                        it.copy(
                            listNotesSearch = useCases.searchNotesUseCase(
                                list = it.listNotes,
                                key = event.key
                            )
                        )
                    }
                }
            }

            is EventScreenNotes.DeleteNote -> {
                job = viewModelScope.launch {
                    useCases.deleteNoteUseCase(event.note.id)
                }
            }
        }
    }
}


