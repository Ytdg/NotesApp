package com.example.notesappapi.feature.presentation.component.screen_to_screen

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesappapi.feature.presentation.util.ItemsDropDownMenu

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    close: () -> Unit,
    list: List<ItemsDropDownMenu>
) {
    MaterialTheme(shapes = Shapes(extraSmall = RoundedCornerShape(18.dp))) {
        DropdownMenu(expanded = expanded, onDismissRequest = { close() }, modifier = modifier) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.actionText, color = item.colorLabel, fontSize = 16.sp) },
                    onClick = { item.action() },
                    trailingIcon = {
                        if (item.imageVector != null) {
                            Icon(
                                imageVector = item.imageVector,
                                contentDescription = null,
                                tint = item.tint
                            )
                        }
                    })
            }
        }
    }
}