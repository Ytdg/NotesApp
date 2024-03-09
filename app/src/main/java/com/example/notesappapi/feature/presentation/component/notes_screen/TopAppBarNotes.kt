package com.example.notesappapi.feature.presentation.component.notes_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    searchNotes: (String) -> Unit,
    showTopAppBar: () -> Unit,
) {
    val keyText = rememberSaveable {
        mutableStateOf("")
    }
    val focus = remember {
        mutableStateOf(false)
    }
    val focusRequster = remember {
        FocusRequester()
    }
    androidx.compose.material3.SearchBar(
        modifier = modifier
            .focusRequester(focusRequster)
            .onFocusChanged {
                focus.value = it.isFocused
            },
        placeholder = { Text(text = "Search...", color = Color.Black.copy(0.6f)) },
        query = keyText.value,
        onQueryChange = { keyText.value = it },
        onSearch = searchNotes,
        active = false,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = { showTopAppBar() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { searchNotes(keyText.value) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }, colors = SearchBarDefaults.colors(
            dividerColor = Color.Transparent, inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black.copy(0.9f),
                unfocusedTextColor = Color.Black.copy(0.9f),
            )
        )
    ) {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNotes(modifier: Modifier,searchNotes: (String?) -> Unit) {
    val isVisibleSearchString = rememberSaveable {
        mutableStateOf(false)
    }
    val isVisibleTopAppBar = rememberSaveable {
        mutableStateOf(true)
    }
    val showTopAppBar = {
        searchNotes(null)
        isVisibleSearchString.value = false
        isVisibleTopAppBar.value = true
    }
    val animatedSearchBar = animateDpAsState(
        targetValue =
        if (isVisibleSearchString.value) {
            5.dp
        } else {
            -5.dp
        }, label = "", animationSpec = tween(durationMillis = 200)
    )
    if (isVisibleTopAppBar.value) {
        androidx.compose.material3.TopAppBar(modifier = modifier, title = {
            Text(
                text = "Notes",
                style = TextStyle(
                    fontSize = 36.sp,
                    color = Color(202, 184, 251),
                    fontWeight = FontWeight(700)
                )
            )
        }, actions = {
            IconButton(onClick = {
                isVisibleTopAppBar.value = false
                isVisibleSearchString.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    Modifier.size(34.dp),
                    tint = Color.Gray
                )
            }
        })
    }
    if (isVisibleSearchString.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = animatedSearchBar.value),
            horizontalArrangement = Arrangement.Center
        ) {
            SearchBar(modifier = Modifier, searchNotes = searchNotes, showTopAppBar = showTopAppBar)
        }
    }
}