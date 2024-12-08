package com.example.notesappjetpackcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTE ORDER BY title ASC")
    fun getNotesByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE ORDER BY dateAdded ASC")
    fun getNotesByDateAdded(): Flow<List<Note>>
}