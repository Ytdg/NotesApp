package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.repository.ApiRepository
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent

class DeleteNoteUseCase(
    val apiRepository: ApiRepository,
    val apiRepositoryContent: ApiRepositoryContent
) {
    suspend operator fun invoke(idNote: Int) {
        val listContent = apiRepository.GetContent(idNote)
        apiRepositoryContent.deleteContent(listContent)
        apiRepository.DeleteNote(idNote)

    }
}