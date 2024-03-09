package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.repository.ApiRepository

class CreateNoteUseCase(val apiRepository: ApiRepository){
    operator suspend fun invoke(note: Note):Int{
        return  apiRepository.CreateNote(note).toInt()
    }
}