package com.example.notesappapi.feature.presentation.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GrandPermission(): ManagedActivityResultLauncher<String, Boolean> {
    val stateDialogPermission = remember {
        mutableStateOf(false)
    }
    val laucherPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            stateDialogPermission.value = !it
        })
    val closeDialogPermission = { stateDialogPermission.value = false }
    if (stateDialogPermission.value) {
        AlertDialogPermission(close = closeDialogPermission)
    }
    return laucherPermission
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertDialogPermission(close: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { /*TODO*/ }, modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Чтобы воспользоваться функцией, нужно предоставить разрешение",
                fontSize = 16.sp
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { close() }, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "ОК", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick ={
                        context.startActivity(
                            Intent(
                               Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package",context.packageName,null)
                            )
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Предоставить разрешение",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }

            }
        }

    }
}

fun CheckPermition(permition: String, context: Context): Boolean =
    context.checkSelfPermission(permition) != PackageManager.PERMISSION_DENIED
