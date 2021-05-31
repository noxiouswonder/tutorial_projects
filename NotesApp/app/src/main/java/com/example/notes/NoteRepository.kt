package com.example.notes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDao: NoteDao) {
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //Used LiveData instead of Flow
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    //We inserted all functions from NoteDao in this repository for providing a cleaner api
    //Hence the DAO won't get data itself, and all data will be passed through this repository
}