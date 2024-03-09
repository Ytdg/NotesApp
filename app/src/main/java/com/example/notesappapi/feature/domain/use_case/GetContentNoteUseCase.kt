package com.example.notesappapi.feature.domain.use_case

import androidx.compose.runtime.mutableStateListOf
import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.model.TypeContent
import com.example.notesappapi.feature.domain.repository.ApiRepository
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent

class GetContentNoteUseCase(
    val apiRepository: ApiRepository,
    val apiRepositoryContent: ApiRepositoryContent
) {
    operator suspend fun invoke(id: Int): List<NoteContent> {
        val listContent = apiRepository.GetContent(id)
        val listPassContent=listContent.filter { it.typeContent==TypeContent.none }
        apiRepositoryContent.deleteContent(listPassContent)
        apiRepository.DeleteContent(listPassContent)
        return  listContent.filter { it.typeContent!=TypeContent.none }
    }
}