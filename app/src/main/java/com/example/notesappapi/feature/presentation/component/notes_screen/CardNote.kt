package com.example.notesappapi.feature.presentation.component.notes_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.CombinedClickableNode
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.example.notesappapi.R
import com.example.notesappapi.feature.domain.model.Note

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CardNote(modifier: Modifier, note: Note, openNote: () -> Unit, openContexMenu: () -> Unit) {
    val interation = remember {
        MutableInteractionSource()
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(note.color)),
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clip(
                RoundedCornerShape(32.dp)
            )
            .combinedClickable(
                interactionSource = interation,
                indication = rememberRipple(true),
                onClick = { openNote() }, onLongClick = {openContexMenu()}),

    ) {
        Column(
            modifier = Modifier.padding(
                start = 30.dp,
                end = 73.dp,
                top = if (note.description.isNotEmpty()) {
                    36.dp
                } else 58.dp, bottom = 40.dp
            ), verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = note.title,
                style = TextStyle(
                    color = Color(49, 33, 97),
                    fontSize = 36.sp,
                    fontFamily = FontFamily(
                        Font(R.font.font_description)
                    ), fontWeight = FontWeight(700)
                )
            )
            DescriptionCardNote(descriptionNote = note.description, colorNote = note.color)
        }
    }
}

@Composable
private fun DescriptionCardNote(descriptionNote: String, colorNote: Int) {
    if (descriptionNote.isNotEmpty()) {
        val colorUtils = ColorUtils.blendARGB(colorNote, Color.Black.toArgb(), 0.4f)
        Text(
            text = descriptionNote,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily(Font(R.font.font_description)),
            color = Color(colorUtils),
            fontSize = 24.sp,
            maxLines = 6,
            fontWeight = FontWeight(500)
        )
    }
}


