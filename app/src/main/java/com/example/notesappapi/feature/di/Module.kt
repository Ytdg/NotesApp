package com.example.notesappapi.feature.di

import android.content.Context
import androidx.room.Room
import com.example.notesappapi.feature.data.api.InternalStorageContentApi
import com.example.notesappapi.feature.data.api.NotesDataBaseApi
import com.example.notesappapi.feature.data.data_base.NotesDataBase
import com.example.notesappapi.feature.data.internal_storage.InternalStorageContent
import com.example.notesappapi.feature.domain.use_case.CreateContentUseCase
import com.example.notesappapi.feature.domain.use_case.DeleteNoteUseCase
import com.example.notesappapi.feature.domain.use_case.GetNotesUseCase
import com.example.notesappapi.feature.domain.use_case.CreateNoteUseCase
import com.example.notesappapi.feature.domain.use_case.DeleteContentUseCase
import com.example.notesappapi.feature.domain.use_case.GetContentNoteUseCase
import com.example.notesappapi.feature.domain.use_case.GetNoteUseCase
import com.example.notesappapi.feature.domain.use_case.PlayRecordAudio
import com.example.notesappapi.feature.domain.use_case.RecordAudioUseCase
import com.example.notesappapi.feature.domain.use_case.SaveEditNoteUseCase
import com.example.notesappapi.feature.domain.use_case.SearchNotesUseCase
import com.example.notesappapi.feature.domain.use_case.UseCases
import com.example.notesappapi.feature.domain.util.audio.PlayerAudio
import com.example.notesappapi.feature.domain.util.audio.RecorderAudio
import com.example.notesappapi.feature.presentation.model.ISharedDataServiseVm
import com.example.notesappapi.feature.presentation.model.SharedDataVm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun UseCaseBulid(
        notesDataBaseApi: NotesDataBaseApi,
        internalStorageContentApi: InternalStorageContentApi,
        @ApplicationContext context: Context
    ): UseCases {
        val getNotesUseCase = GetNotesUseCase(notesDataBaseApi)
        val createNoteUseCase = CreateNoteUseCase(notesDataBaseApi)
        val deleteNoteUseCase = DeleteNoteUseCase(notesDataBaseApi,internalStorageContentApi)
        val saveEditNoteUseCase = SaveEditNoteUseCase(notesDataBaseApi,internalStorageContentApi)
        val getNoteUseCase = GetNoteUseCase(notesDataBaseApi)
        val searchNotesUseCase = SearchNotesUseCase(notesDataBaseApi)
        val getContentNoteUseCase=GetContentNoteUseCase(notesDataBaseApi,internalStorageContentApi)
        val deleteContentUseCase=DeleteContentUseCase(internalStorageContentApi,notesDataBaseApi)
        val createContentUseCase=CreateContentUseCase(notesDataBaseApi)
        val playRecordAudioUseCase = PlayRecordAudio(playerAudioProvider = PlayerAudio(context), apiRepositoryContent =internalStorageContentApi )
        val recordAudioUseCase = RecordAudioUseCase(recorderAudioProvider = RecorderAudio(context), apiRepositoryContent = internalStorageContentApi)
        val useCases = UseCases(
            getNotesUseCase,
            createNoteUseCase,
            deleteNoteUseCase,
            getNoteUseCase,
            saveEditNoteUseCase,
            searchNotesUseCase,
            getContentNoteUseCase,
            playRecordAudioUseCase,
            recordAudioUseCase,
            deleteContentUseCase,
            createContentUseCase
        )
        return useCases
    }
    @Provides
    @Singleton
    fun BuildSharedDataService():ISharedDataServiseVm{
        val iSharedDataServiseVm=SharedDataVm()
        return iSharedDataServiseVm
    }
    @Provides
    @Singleton
    fun createReopsitoryContent(@ApplicationContext context:Context):InternalStorageContentApi{
        val internalStorageContentApi=InternalStorageContentApi(InternalStorageContent(context))
        return  internalStorageContentApi
    }
    @Provides
    @Singleton
    fun CreateDataBaseNote(@ApplicationContext context: Context): NotesDataBaseApi {
        val room =
            Room.databaseBuilder(context = context, NotesDataBase::class.java, "NoteDataBase")
                .build()
        return NotesDataBaseApi(room.notesDao)
    }
}
