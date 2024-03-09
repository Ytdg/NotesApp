package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.repository.ApiRepository

class CreateContentUseCase(val apiRepository: ApiRepository) {
    suspend fun create(noteContent: NoteContent): Int {
        return apiRepository.CreateContent(noteContent).toInt()
    }
}