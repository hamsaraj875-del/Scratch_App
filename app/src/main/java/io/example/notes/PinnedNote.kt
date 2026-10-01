package io.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.example.notes.room.Note

class PinnedNote(
    private var notes: List<Note>,
    private val pin: (Note) -> Unit,
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<PinnedNote.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.noteTitle)
        val content: TextView = itemView.findViewById(R.id.noteContent)
        val date: TextView = itemView.findViewById(R.id.noteDate)
        val pin: ImageView = itemView.findViewById(R.id.btnPin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pinned_note_card, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.title.text = note.title
        holder.content.text = note.content
        holder.date.text = note.date

        holder.itemView.setOnClickListener {
            onNoteClick(note)
        }

        holder.pin.setOnClickListener {
            pin(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}