package com.example.notesappapi.feature.domain.use_case

class UseCases(
    val getNotesUseCase: GetNotesUseCase,
    val createNoteUseCase: CreateNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val saveEditNoteUseCase: SaveEditNoteUseCase,
    val searchNotesUseCase: SearchNotesUseCase,
    val getContentNoteUseCase: GetContentNoteUseCase,
    val playRecordAudio: PlayRecordAudio,
    val recordAudioUseCase: RecordAudioUseCase,
    val deleteContentUseCase: DeleteContentUseCase,
    val createContentUseCase: CreateContentUseCase
)