package io.example.notes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao{

    @Insert
    suspend fun insertNote(note:Note):Long;


    @Query("DELETE FROM notes WHERE id = :noteId AND userId = :userId")
    suspend fun deleteNote(userId: Int, noteId: Int): Int

    @Query("SELECT * FROM notes WHERE userId = :userId")
    fun getAllNotesOfUser(userId:Int): LiveData<List<Note>>;

}