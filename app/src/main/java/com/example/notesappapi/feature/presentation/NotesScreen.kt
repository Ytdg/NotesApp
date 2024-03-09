package com.example.notesappapi.feature.presentation

import android.media.RouteListingPreference.Item
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesappapi.NavDestination
import com.example.notesappapi.NavDestinationArgumentsDefaultValue
import com.example.notesappapi.feature.domain.model.Note
import com.example.notesappapi.feature.presentation.component.notes_screen.BottomBarNotes
import com.example.notesappapi.feature.presentation.component.notes_screen.CardNote
import com.example.notesappapi.feature.presentation.component.notes_screen.TopAppBarNotes
import com.example.notesappapi.feature.presentation.component.screen_to_screen.DropDownMenu
import com.example.notesappapi.feature.presentation.events.EventScreenNotes
import com.example.notesappapi.feature.presentation.model.ViewModelNotes
import com.example.notesappapi.feature.presentation.util.ItemsDropDownMenu
import com.example.notesappapi.feature.presentation.util.keyboardAsState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(viewModelNotese: ViewModelNotes = hiltViewModel(), navController: NavController) {
    val coroutine = rememberCoroutineScope()
    /*stateNotes*/
    val stateNotes = viewModelNotese.stateNotes.collectAsState().value
    val listNote = {
        stateNotes.listNotesSearch.ifEmpty {
            stateNotes.listNotes
        }
    }
    /*Modifier*/
    val modifierScaffold = Modifier
        .fillMaxSize()
    val modifierTopAppBar = Modifier
        .fillMaxWidth()
        .graphicsLayer {
            this.shadowElevation = 20f;this.spotShadowColor =
            Color.Black;this.ambientShadowColor = Color.Black
        }
    val modifierBottomBar = Modifier
    /*Focus*/
    val localFocus = LocalFocusManager.current
    /*KeyBoard*/
    val imePaddingValues = WindowInsets.ime.asPaddingValues()
    val isKeyboardOpen by keyboardAsState()
    LaunchedEffect(key1 = isKeyboardOpen) {
        if (!isKeyboardOpen) {
            delay(100)
            localFocus.clearFocus()
        }
    }
    /*LazyColumn*/
    val lazyColumnState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        delay(50)
        lazyColumnState.animateScrollToItem(0)
    }
    val stopScroll = { coroutine.launch { lazyColumnState.stopScroll(); } }
    /*navigation*/
    val openNoteScreen =
        { id: Int ->
            stopScroll()
            navController.navigate(NavDestination.noteScreen + "?id=${id}")
        }

    Scaffold(
        modifier = modifierScaffold,
        topBar = {
            TopAppBarNotes(
                modifier = modifierTopAppBar,
                searchNotes = { key: String? -> viewModelNotese.OnEvent(EventScreenNotes.Search(key = key)) })
        },
        bottomBar = {
            BottomBarNotes(
                modifier = modifierBottomBar,
                openNoteScreen = openNoteScreen
            )
        }) { paddingValues ->

        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top =
                    paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 10.dp),
        ) {
            itemsIndexed(
                listNote(),
                key = { index: Int, item: Note -> item.timeSave }) { index, note ->
                val dropDownMenuState = remember {
                    mutableStateOf(false)
                }
                val stateVisibleNote = remember {
                    mutableStateOf(true)
                }
                Box(
                    modifier = Modifier.animateItemPlacement(tween(250)),
                    contentAlignment = Alignment.CenterStart
                ) {
                        CardNote(
                            modifier = Modifier
                                .width(327.dp)
                                .defaultMinSize(minHeight = 223.dp)
                                .padding(horizontal = 32.dp),
                            note = note,
                            openNote = { openNoteScreen(note.id) },
                            openContexMenu = { dropDownMenuState.value = true }
                        )

                    Box() {
                        DropDownMenu(
                            expanded = dropDownMenuState.value,
                            close = { dropDownMenuState.value = false },
                            list = remember {
                                listOf(
                                    ItemsDropDownMenu(
                                        actionText = "Delete",
                                        imageVector = Icons.Default.Delete,
                                        action = {
                                            dropDownMenuState.value=false
                                            stateVisibleNote.value = false
                                            viewModelNotese.OnEvent(
                                                EventScreenNotes.DeleteNote(
                                                    note
                                                )
                                            )
                                        },
                                        colorLabel = Color.Unspecified,
                                        tint = Color.Red
                                    )
                                )
                            })
                    }
                }
            }
            item {
                Spacer(
                    modifier = Modifier.height(
                        if (isKeyboardOpen) {
                            imePaddingValues.calculateBottomPadding() + 10.dp
                        } else {
                            10.dp
                        }
                    )
                )
            }
        }
    }
}


