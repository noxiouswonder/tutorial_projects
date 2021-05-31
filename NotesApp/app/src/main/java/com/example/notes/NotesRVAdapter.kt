package com.example.notes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRVAdapter(private val context: Context, private val listener: INotesRVAdapter): RecyclerView.Adapter<NotesRVAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val viewHolder = NotesViewHolder(view)
        viewHolder.deleteImageView.setOnClickListener{
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteTextView.text = allNotes[position].text
    }

    override fun getItemCount(): Int {
        Log.d("size", allNotes.size.toString())
        return allNotes.size
    }

    fun updateList(notesList: List<Note>){
        allNotes.clear()
        allNotes.addAll(notesList)
        Log.d("updated size", allNotes.size.toString())
        notifyDataSetChanged()
    }
}

interface INotesRVAdapter{
    fun onItemClicked(note: Note)
}