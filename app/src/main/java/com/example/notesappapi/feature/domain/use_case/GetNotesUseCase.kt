package com.example.notesappapi.feature.domain.use_case
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
class GetNotesUseCase(val apiRepository: ApiRepository){
    operator fun  invoke():Flow<List<Note>>{
        return apiRepository.getNotes()
    }
}