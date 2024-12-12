package com.example.notesappjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesappjetpackcompose.presentation.components.AddNoteScreen
import com.example.notesappjetpackcompose.presentation.components.NotesScreen
import com.example.notesappjetpackcompose.presentation.viewmodel.NoteViewModel
import com.example.notesappjetpackcompose.ui.theme.NotesAppJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppJetpackComposeTheme {
                val viewModel: NoteViewModel = hiltViewModel()
                Surface(
                    modifier = Modifier.systemBarsPadding().fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "notesScreen"
                    ){
                        composable(
                            route = "notesScreen",
                        ){
                            NotesScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }

                        composable(
                            route = "addNoteScreen",
                        ){
                            AddNoteScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }

            }
        }
    }
}
