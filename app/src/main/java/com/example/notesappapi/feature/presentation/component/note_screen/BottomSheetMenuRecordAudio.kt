package com.example.notesappapi.feature.presentation.component.note_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notesappapi.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomMenuRecordAudio(
    close: () -> Unit,
    startRecording: () -> Unit,
    stopRecording: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val lotiyComposition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.recording_a))
    val isPlaying = remember {
        mutableStateOf(false)
    }
    val speed = remember {
        mutableStateOf(2f)
    }
    val proccesRecording = {
        if (!isPlaying.value) {
            startRecording();isPlaying.value = true
        } else {
            scope.launch {
                delay(200);sheetState.hide();close();stopRecording();isPlaying.value = false
            }
        }
    }

    val progress = animateLottieCompositionAsState(
        composition = lotiyComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying.value,
        speed = speed.value,
        restartOnPlay = true
    )
    ModalBottomSheet(
        onDismissRequest = { scope.launch { sheetState.hide();close();stopRecording() } },
        modifier = Modifier,
        sheetState = sheetState
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Record",
                    fontSize = 40.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(
                        Font(R.font.font_description)
                    ),
                    color = Color(137, 98, 248)
                )
                LottieAnimation(
                    composition = lotiyComposition.value,
                    progress = if (isPlaying.value) progress.progress else 0f,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 10.dp)
                )
            }
            IconButton(onClick = { proccesRecording() }, modifier = Modifier.padding(top = 10.dp)) {
                Icon(
                    imageVector = if (isPlaying.value) ImageVector.vectorResource(id = R.drawable.icons8_pause_50) else ImageVector.vectorResource(
                        id = R.drawable.play__4_
                    ),
                    contentDescription = null,
                    tint = Color(137, 98, 248)
                )
            }
        }
    }

}