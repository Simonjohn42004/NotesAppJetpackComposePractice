package com.example.notesappjetpackcompose.presentation.viewmodel

import com.example.notesappjetpackcompose.data.Note
import com.example.notesappjetpackcompose.utils.SortType

sealed interface NoteEvents {
    object SaveNote: NoteEvents
    data class SetTitle(val title: String): NoteEvents
    data class SetDescription(val description: String): NoteEvents
    data class DeleteNote(val note: Note): NoteEvents
    data class SortNotes(val sortType: SortType): NoteEvents
}