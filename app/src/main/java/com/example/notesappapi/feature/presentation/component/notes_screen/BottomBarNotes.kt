package com.example.notesappapi.feature.presentation.component.notes_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.notesappapi.NavDestinationArgumentsDefaultValue
import com.example.notesappapi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarNotes(modifier: Modifier, openNoteScreen: (Int) -> Unit) {
    BottomAppBar(modifier = modifier.graphicsLayer {
        this.shadowElevation = 50f;this.spotShadowColor =
        Color.Black;
        ambientShadowColor = Color.Black;
    }, containerColor = Color.White, contentPadding = PaddingValues(0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(onClick = {openNoteScreen(NavDestinationArgumentsDefaultValue.passId)}, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}
/*
Card(
modifier = modifier,
colors = CardDefaults.cardColors(containerColor = Color.White),
elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
shape = RoundedCornerShape(0.dp)
) {
    Row(
        modifier=Modifier.fillMaxWidth().padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.plus),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }

}

 */

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    compression: Float = 1f
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        if (compression != 0f) {
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height / compression,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}

