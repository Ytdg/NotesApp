package com.example.notesappapi.feature.presentation.util


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ItemsDropDownMenu(
    val actionText: String,
    val imageVector: ImageVector?,
    val action:()->Unit,
    val colorLabel:Color,
    val tint:Color
)
