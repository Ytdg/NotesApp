package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.repository.ApiRepository

class GetNoteUseCase(val apiRepository: ApiRepository){
    suspend operator  fun invoke(id:Int): Note{
        return  apiRepository.GetNote(id)
    }
}