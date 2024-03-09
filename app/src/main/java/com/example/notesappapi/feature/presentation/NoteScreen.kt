package com.example.notesappapi.feature.presentation

import android.Manifest
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesappapi.feature.presentation.component.note_screen.BottomMenuRecordAudio
import com.example.notesappapi.feature.presentation.component.note_screen.SongContent
import com.example.notesappapi.feature.presentation.component.note_screen.TextField
import com.example.notesappapi.feature.presentation.component.note_screen.TopAppBarNote
import com.example.notesappapi.feature.presentation.component.screen_to_screen.DropDownMenu
import com.example.notesappapi.feature.presentation.component.screen_to_screen.MenuColor
import com.example.notesappapi.feature.presentation.events.EventEditContent
import com.example.notesappapi.feature.presentation.events.EventEditNote
import com.example.notesappapi.feature.presentation.model.ViewModelContent
import com.example.notesappapi.feature.presentation.model.ViewModelNote
import com.example.notesappapi.feature.presentation.util.CheckPermition
import com.example.notesappapi.feature.presentation.util.GrandPermission
import com.example.notesappapi.feature.presentation.util.ItemsDropDownMenu
import com.example.notesappapi.feature.presentation.util.LauncherGallery
import com.example.notesappapi.feature.presentation.util.keyboardAsState
import com.example.notesappapi.ui.TextFieldColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    viewModelNote: ViewModelNote = hiltViewModel(),
    viewModelContent: ViewModelContent = hiltViewModel(),
    navController: NavController
) {
    /*StateNoteComponent*/
    val stateNote = viewModelNote.stateNote.collectAsState().value
    val stateContent = viewModelContent.stateNoteContent.collectAsState().value
    val stateAudioPlayer = viewModelContent.stateAudioPlayer.value
    /*Util*/
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation
    val keyboardController = LocalSoftwareKeyboardController.current
    val detectKeyBoard = keyboardAsState()
    val paddingIme = WindowInsets.ime.asPaddingValues()
    val coroutineScope = rememberCoroutineScope()
    /*StateItemTopAppBar*/
    val stateMenuColor = remember {
        mutableStateOf(false)
    }
    val menuColor = { value: Boolean ->
        stateMenuColor.value = value
    }
    val stateEditContent = remember {
        mutableStateOf(false)
    }
    val editContent = { value: Boolean ->
        stateEditContent.value = value
        if (!value) {
            coroutineScope.launch {
                keyboardController?.hide()
            }
        }
    }
    /*Focus*/
    val localFocus = LocalFocusManager.current
    LaunchedEffect(key1 = detectKeyBoard.value) {
        delay(50)
        if (!detectKeyBoard.value) {
            localFocus.clearFocus();editContent(false)
        } else {
            editContent(true)
        }
    }
    /*PermitionLauncher*/
    val grandPermission = GrandPermission()
    /*Recording*/
    val stateBottomSheetRecording = remember {
        mutableStateOf(false)
    }
    val launcherGallery= LauncherGallery()
    val openGallery={
        if (CheckPermition(
                Manifest.permission.READ_MEDIA_IMAGES,
                context
            ) || Build.VERSION.SDK_INT<Build.VERSION_CODES.TIRAMISU
        )
        {
            launcherGallery.launch("image/*")
        }
        else{
            grandPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }
    val showSheetRecording = {
        if (CheckPermition(
                Manifest.permission.RECORD_AUDIO,
                context
            )
        ) {
            stateBottomSheetRecording.value = true
        } else {
            grandPermission.launch(Manifest.permission.RECORD_AUDIO); stateBottomSheetRecording.value =
                false
        }
    }
    Scaffold(
        Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            Box(contentAlignment = Alignment.BottomEnd) {
                TopAppBarNote(
                    modifier = Modifier,
                    openMenuColor = menuColor,
                    stateMenuColor = stateMenuColor,
                    selectedColor = stateNote.color,
                    editContentState = stateEditContent,
                    editingContent = editContent,
                    showSheetRecording = showSheetRecording,
                    openGallery = openGallery
                )
                Box() {
                    MenuColor(
                        state = stateMenuColor,
                        menuColor = menuColor,
                        offset = DpOffset(
                            if (orientation == Configuration.ORIENTATION_LANDSCAPE) (130.dp) else 30.dp,
                            0.dp
                        ),
                        selectColor = { color ->
                            viewModelNote.onEvent(
                                EventEditNote.SelectColor(
                                    color
                                )
                            )
                        },
                        selectedColor = stateNote.color
                    )
                }
            }
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                ),
            verticalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(
                start =
                if (orientation == Orientation.RIGHT_LEFT.ordinal) {
                    paddingValues.calculateLeftPadding(LayoutDirection.Ltr) + 10.dp
                } else 31.dp,
                end = 31.dp
            )
        ) {
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldValue = stateNote.title,
                    onValueChange = { value -> viewModelNote.onEvent(EventEditNote.EditTitle(value)) },
                    textStyle = TextStyle(fontSize = 48.sp),
                    textFieldColors = TextFieldColors()
                ) {
                    Text(
                        text = "Title",
                        style = TextStyle(fontSize = 48.sp, color = Color(169, 144, 221))
                    )
                }
            }
            item {
                Text(
                    text = viewModelNote.dateCreatingNote,
                    style = TextStyle(color = Color(137, 98, 248), fontSize = 18.sp)
                )
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldValue = stateNote.description,
                    onValueChange = { value ->
                        viewModelNote.onEvent(
                            EventEditNote.EditDescription(
                                value
                            )
                        )
                    },
                    textStyle = TextStyle(fontSize = 24.sp),
                    textFieldColors = TextFieldColors()
                ) {
                    Text(
                        text = "Description",
                        style = TextStyle(fontSize = 24.sp, color = Color(169, 144, 221))
                    )
                }
            }
            itemsIndexed(stateContent, key = { index, item -> item.idContent}) { index, item ->
                val stateDropDownMenu = remember {
                    mutableStateOf(false)
                }
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    SongContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        onPlay = { viewModelContent.onEvent(EventEditContent.PlaySong(item)) },
                        onStop = { viewModelContent.onEvent(EventEditContent.StopSong()) },
                        isActive = stateAudioPlayer.idContent == item.idContent,
                        cardColors = CardDefaults.cardColors(
                            containerColor = stateNote.color.copy(
                                0.5f
                            )
                        ),
                        duration = item.duration!!,
                        openContexMenu = { stateDropDownMenu.value = true }
                    )
                    Box {
                        DropDownMenu(
                            expanded = stateDropDownMenu.value,
                            close = { stateDropDownMenu.value = false },
                            list = remember{
                                listOf(
                                    ItemsDropDownMenu(
                                        action = {viewModelContent.onEvent(EventEditContent.DeleteContent(item))},
                                        actionText = "Remove",
                                        imageVector = Icons.Default.Delete,
                                        colorLabel = Color.Unspecified,
                                        tint = Color.Red
                                    )
                                )
                            }
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(paddingIme.calculateBottomPadding()+paddingValues.calculateBottomPadding()+10.dp))
            }
        }
    }
    if (stateBottomSheetRecording.value) {
        BottomMenuRecordAudio(
            close = { stateBottomSheetRecording.value = false },
            startRecording = { viewModelContent.onEvent(EventEditContent.StartRecording()) },
            stopRecording = { viewModelContent.onEvent(EventEditContent.StopRecording()) }
        )
    }
    BackHandler(enabled = true) {
        viewModelNote.onEvent(EventEditNote.SaveNote(list = viewModelContent.getListNoteContent()))
        navController.popBackStack()
    }
}


