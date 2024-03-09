package com.example.notesappapi.feature.presentation.util

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun LauncherGallery(): ManagedActivityResultLauncher<String, Uri?> {
    val context= LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
        }
    )
    return launcher
}