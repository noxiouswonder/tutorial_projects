package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.notesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer{list ->
            list?.let{
                adapter.updateList(it)
            }
        })
        initialize()
    }

    private fun initialize() {
        val note = Note("Initial")
        insertNote(note)
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
    }

    fun insertNote(note: Note){
        viewModel.insertNote(note)
    }

    fun createNote(view: View) {
        val noteEditText: EditText = findViewById(R.id.noteEditText)
        val text = noteEditText.text.toString()
        if(text.isNotEmpty()){
            val note = Note(text)
            insertNote(note)
        }
    }

}