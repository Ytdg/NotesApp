package com.example.notesappapi.feature.domain.use_case

import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.domain.repository.ApiRepository

class SearchNotesUseCase(apiRepository: ApiRepository){
    operator fun invoke(list: List<Note>,key:String?):List<Note>{
        if(key!=null) {
            val findNotesList = list.filter {
                it.title.contains(key,ignoreCase = true)
            }
            return findNotesList
        }
        return emptyList()
    }
}