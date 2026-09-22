package io.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.example.notes.room.Note

class NoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.noteTitle)
        val content: TextView = itemView.findViewById(R.id.noteContent)
        val date: TextView = itemView.findViewById(R.id.noteDate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_card_view, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = notes[position]

        holder.title.text = note.title
        holder.content.text = note.content
        holder.date.text = note.date
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}