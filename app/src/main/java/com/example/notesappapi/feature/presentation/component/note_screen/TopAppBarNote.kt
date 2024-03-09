package com.example.notesappapi.feature.presentation.component.note_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.notesappapi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNote(
    modifier: Modifier,
    openMenuColor: (Boolean) -> Unit,
    stateMenuColor: State<Boolean>,
    selectedColor: Color,
    editContentState: State<Boolean>,
    editingContent: (Boolean) -> Unit,
    showSheetRecording:()->Unit,
    openGallery:()->Unit
) {
    val sizeIconColor = animateDpAsState(
        targetValue =
        if (stateMenuColor.value) 48.dp else 38.dp, label = "",
        animationSpec = tween(delayMillis = 150, easing = LinearOutSlowInEasing)
    )
    val animatedCheck = animateColorAsState(
        targetValue =
        if (editContentState.value) Color(137, 98, 248) else Color(
            231,
            219,
            255
        ), label = "", animationSpec = tween(100)
    )
    TopAppBar(
        modifier = modifier,
        title = { /*TODO*/ },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color(95, 81, 124)
                )
            }
        },
        actions = {
            IconButton(onClick = {  showSheetRecording() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.voice_3),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { openMenuColor(true) }) {
                Canvas(modifier = Modifier) {
                    this.drawCircle(selectedColor, radius = sizeIconColor.value.value)
                }
            }
            IconButton(onClick = { openGallery() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.camera),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { editingContent(false) }, enabled = editContentState.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = animatedCheck.value,
                    modifier = Modifier.size(34.dp)
                )
            }
        })
}