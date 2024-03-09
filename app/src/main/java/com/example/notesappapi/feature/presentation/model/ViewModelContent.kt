package com.example.notesappapi.feature.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappapi.NavDestinationArgumentsDefaultValue
import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.model.TypeContent
import com.example.notesappapi.feature.domain.use_case.UseCases
import com.example.notesappapi.feature.presentation.events.EventEditContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelContent @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle,
    private val sharedDataVm: ISharedDataServiseVm
) : ViewModel() {
    private val _stateNoteContent: MutableStateFlow<SnapshotStateList<StateNoteContent>> =
        MutableStateFlow(
            mutableStateListOf()
        )
    val stateNoteContent: StateFlow<List<StateNoteContent>> = _stateNoteContent
    private val _stateAudioPlayer: MutableState<StateAudioPlayer> =
        mutableStateOf(StateAudioPlayer())
    val stateAudioPlayer: State<StateAudioPlayer> = _stateAudioPlayer
    var recendlyCreatedContentId: Int? = null

    private var job: Job = Job()

    init {
        val idNote = savedStateHandle.get<Int>("id")!!
        viewModelScope.launch() {
            if (idNote != NavDestinationArgumentsDefaultValue.passId) {
                convertToListStateNoteContent(useCases.getContentNoteUseCase(idNote))
            }
        }
    }

    fun onEvent(event: EventEditContent) {
        job.cancel()
        when (event) {
            is EventEditContent.PutPhoto ->{

            }

            is EventEditContent.DeleteContent -> {
                _stateNoteContent.value.remove(event.stateContent)
                viewModelScope.launch {
                    useCases.deleteContentUseCase(
                        list = listOf(
                            NoteContent(
                                typeContent = event.stateContent.typeContent,
                                parentId = sharedDataVm.idNote,
                                id=event.stateContent.idContent
                            )
                        )
                    )
                }
            }

            is EventEditContent.PlaySong -> {
                _stateAudioPlayer.value = StateAudioPlayer(idContent = event.stateContent.idContent)
                job = viewModelScope.launch {
                    useCases.playRecordAudio.Play(
                        idContent = event.stateContent.idContent,
                        stopCallBack = {
                            _stateAudioPlayer.value = StateAudioPlayer(idContent = null)
                        })
                }
            }

            is EventEditContent.StartRecording -> {
                job = viewModelScope.launch {
                    val idContent =
                        useCases.createContentUseCase.create(
                            NoteContent(
                                parentId = sharedDataVm.idNote,
                                typeContent = TypeContent.none
                            )
                        )
                    recendlyCreatedContentId = idContent
                    useCases.recordAudioUseCase.startRecord(
                        idContent = idContent,
                    )
                }
            }

            is EventEditContent.StopRecording -> {
                if (recendlyCreatedContentId != null) {
                    val audioFile = useCases.recordAudioUseCase.stopRecord()
                    _stateNoteContent.value.add(
                        StateNoteContent(
                            typeContent = TypeContent.audio,
                            duration = useCases.recordAudioUseCase.getDurationAudioFile(audioFile) + " " + "sec",
                            idContent = recendlyCreatedContentId!!
                        )
                    )
                    recendlyCreatedContentId = null
                }
            }

            is EventEditContent.StopSong -> {
                useCases.playRecordAudio.Stop()
                _stateAudioPlayer.value = StateAudioPlayer(idContent = null)
            }
        }
    }

    private fun convertToListStateNoteContent(list: List<NoteContent>) {
        list.forEach {
            _stateNoteContent.value.add(
                StateNoteContent(
                    text = it.text,
                    typeContent = it.typeContent,
                    duration = it.duration,
                    isActive = false,
                    idContent = it.id
                )
            )
        }
    }

    fun getListNoteContent(): List<NoteContent> {
        val listContent = mutableListOf<NoteContent>()
        _stateNoteContent.value.forEach {
            listContent.add(
                NoteContent(
                    text = it.text,
                    typeContent = it.typeContent,
                    parentId = sharedDataVm.idNote,
                    duration = it.duration,
                    id = it.idContent
                )
            )
        }
        return listContent
    }

}