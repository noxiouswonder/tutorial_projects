package com.example.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note){
        Log.d("IO", note.text)
        return
    }

    @Delete
    suspend fun delete(note: Note){
        return
    }

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}