package com.example.notesappjetpackcompose.data.di

import android.content.Context
import androidx.room.Room
import com.example.notesappjetpackcompose.data.NoteDao
import com.example.notesappjetpackcompose.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = NoteDatabase::class.java,
            name = "note.db"
        ).build()
    }

    @Provides
    fun provideDao(database: NoteDatabase): NoteDao {
        return database.dao
    }

}