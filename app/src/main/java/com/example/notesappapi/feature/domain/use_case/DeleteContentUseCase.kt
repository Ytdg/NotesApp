package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.NoteContent
import com.example.notesappapi.feature.domain.repository.ApiRepository
import com.example.notesappapi.feature.domain.repository.ApiRepositoryContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeleteContentUseCase(
    val apiRepositoryContent: ApiRepositoryContent,
    val apiRepository: ApiRepository
) {
    operator suspend fun invoke(list: List<NoteContent>) {
        apiRepositoryContent.deleteContent(list)
        apiRepository.DeleteContent(list)
    }
}