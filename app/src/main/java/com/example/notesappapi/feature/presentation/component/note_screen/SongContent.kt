package com.example.notesappapi.feature.presentation.component.note_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesappapi.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SongContent(
    modifier: Modifier,
    onPlay: () -> Unit,
    onStop: () -> Unit,
    openContexMenu:()->Unit,
    isActive: Boolean,
    cardColors: CardColors,
    duration: String
) {
    val interation= remember {
        MutableInteractionSource()
    }
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).combinedClickable(
            interactionSource = interation,
            indication = rememberRipple(true),
            onClick = { if (isActive) onStop() else onPlay() }, onLongClick = {openContexMenu()}),
        colors = cardColors,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (!isActive) {
                    ImageVector.vectorResource(R.drawable.play_update)
                } else {
                    ImageVector.vectorResource(id = R.drawable.icons8_pause_50)
                },
                contentDescription = null,
                tint = Color(137, 98, 248)
            )
            Text(
                text = duration,
                fontSize = 17.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 30.dp),
                color=Color(137, 98, 248)
            )
        }
    }
}