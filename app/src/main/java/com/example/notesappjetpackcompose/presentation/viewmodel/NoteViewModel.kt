package com.example.notesappjetpackcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappjetpackcompose.data.Note
import com.example.notesappjetpackcompose.data.NoteDao
import com.example.notesappjetpackcompose.utils.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModel(
    private val dao: NoteDao
): ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    private val _sortType = MutableStateFlow(_state.value.sortType)


    private val _notes = _sortType
        .flatMapLatest {
            when(it) {
                SortType.By_Title -> {
                    dao.getNotesByTitle()
                }
                SortType.By_Date_Added -> {
                    dao.getNotesByDateAdded()
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val state = combine(_state, _sortType, _notes ) {state, sortType, notes ->
        state.copy(
            notesList = notes,
            sortType = sortType
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = NoteState()
    )


    fun onEvent(event: NoteEvents){
        when(event){
            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvents.SaveNote -> {
                val title = state.value.title
                val description = state.value.description
                val dateAdded = state.value.dateAdded

                if(title.isBlank() || description.isBlank()){
                    return
                }
                val note = Note(
                    title = title,
                    description = description,
                    dateAdded = dateAdded
                )

                viewModelScope.launch {
                    dao.upsertNote(note)
                }
            }
            is NoteEvents.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is NoteEvents.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is NoteEvents.SortNotes -> {
                _sortType.value = event.sortType
            }
        }
    }
}