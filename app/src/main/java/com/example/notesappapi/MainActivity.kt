package com.example.notesappapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesappapi.feature.presentation.NoteScreen
import com.example.notesappapi.feature.presentation.NotesScreen
import com.example.notesappapi.feature.presentation.component.note_screen.BottomMenuRecordAudio
import com.example.notesappapi.feature.presentation.component.note_screen.TextField
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.TextStyle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NavRoot()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("1212","lolKek")
    }
    @Composable
    fun NavRoot() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavDestination.notesScreen) {
            composable(route = NavDestination.notesScreen) {
                NotesScreen(navController = navController)

            }
            composable(
                route = NavDestination.noteScreen + "?id={id}",
                arguments = listOf(navArgument("id") {
                    defaultValue = NavDestinationArgumentsDefaultValue.passId
                }),
                enterTransition = {
                    fadeIn(tween(500)) + slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    fadeOut(tween(300)) + slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(300)
                    )
                }
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments!!.getInt("id")
                NoteScreen(navController = navController)
            }
        }
    }
}