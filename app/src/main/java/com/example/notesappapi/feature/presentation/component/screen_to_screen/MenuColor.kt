package com.example.notesappapi.feature.presentation.component.screen_to_screen

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.notesappapi.R
import com.example.notesappapi.feature.domain.model.ConstColorNote

@Composable
fun MenuColor(
    modifier: Modifier = Modifier,
    state: State<Boolean>,
    menuColor: (Boolean) -> Unit,
    selectColor:(Color)->Unit,
    selectedColor: Color,
    offset: DpOffset
) {
    MaterialTheme(shapes = Shapes(extraSmall = RoundedCornerShape(18.dp))) {
        DropdownMenu(
            expanded = state.value,
            modifier = modifier.background(Color.White),
            onDismissRequest = {menuColor(false)},
            offset = offset
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                ConstColorNote.colorsInt.forEach { colorItem ->
                    IconButton(onClick = { selectColor(colorItem)}) {
                        if (selectedColor == colorItem) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ellipse_8),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(137, 98, 248)
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ellipse_9),
                                contentDescription = null,
                                tint = colorItem
                            )
                        }
                    }
                }
            }
        }
    }
}