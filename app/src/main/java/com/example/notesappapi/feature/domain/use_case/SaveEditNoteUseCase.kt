package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.repository.ApiRepository
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent

class SaveEditNoteUseCase(val apiRepository: ApiRepository,val apiRepositoryContent: ApiRepositoryContent){
    suspend operator  fun  invoke(note: Note,noteContent: List<NoteContent>){
        if(!(note.description.isEmpty()&&note.title.isEmpty()&&noteContent.isEmpty())){
            var _note=note
            if(note.title.isEmpty()){
                _note=note.copy(title = "Новая заметка")
            }
            apiRepository.saveNoteWithContent(_note, noteContent)
        }
        else{
            apiRepository.DeleteNote(note.id)
            apiRepositoryContent.deleteContent(noteContent)
        }
    }
}