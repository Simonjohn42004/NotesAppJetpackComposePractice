package com.example.notesappjetpackcompose.presentation.viewmodel

import com.example.notesappjetpackcompose.data.Note
import com.example.notesappjetpackcompose.utils.SortType

data class NoteState(
    val notesList: List<Note> = emptyList(),
    val title: String = "",
    val description: String = "",
    val sortType: SortType = SortType.By_Title,
    val timeStamp: String = ""
)