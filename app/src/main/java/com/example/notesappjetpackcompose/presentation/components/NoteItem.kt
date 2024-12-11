package com.example.notesappjetpackcompose.presentation.components

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.notesappjetpackcompose.data.Note
import com.example.notesappjetpackcompose.presentation.viewmodel.NoteEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NoteItem(
    note: Note,
    onEvent: (NoteEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    scope.launch{
                        delay(300)
                        onEvent(NoteEvents.DeleteNote(note))
                    }

                }
                SwipeToDismissBoxValue.EndToStart -> {
                    scope.launch{
                        delay(100)
                        onEvent(NoteEvents.DeleteNote(note))
                    }
                }
                SwipeToDismissBoxValue.Settled -> {
                    return@rememberSwipeToDismissBoxState false
                }
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = {
            it * 0.25f
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {},
    ) {
        NoteCard(
            note = note,
            onEvent = onEvent
        )
    }
}