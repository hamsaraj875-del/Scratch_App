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

    @Delete
    suspend fun deleteNote(note:Note);


    @Query("SELECT * FROM notes WHERE userId = :userId")
    fun getAllNotesOfUser(userId:Int): LiveData<List<Note>>;

}