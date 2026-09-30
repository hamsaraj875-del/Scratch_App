package io.example.notes.repository

import androidx.lifecycle.LiveData
import io.example.notes.room.Note
import io.example.notes.room.NoteDao
import io.example.notes.room.User
import io.example.notes.room.UserDao

class DatabaseRepository(private val userDao: UserDao, private val noteDao: NoteDao) {



    //Users Data Provider
    suspend fun insert(user: User):Long{
        return userDao.insertUser(user);
    }

    fun getUserById(userId:Int): LiveData<User> {
        return userDao.getUserById(userId);
    }

    suspend fun updateName(userId:Int,name:String):Int{
        return userDao.updateName(userId,name);
    }

    suspend fun updateEmail(userId:Int,name:String):Int{
        return userDao.updateEmail(userId,name);
    }

    suspend fun updatePassword(userId:Int,newPassword:String):Int{
        return userDao.updateUserPassword(userId,newPassword);
    }

    suspend fun getUser(userId:Int):User{
        return userDao.getUser(userId);
    }

    suspend fun getLoggedInUser():User?{
        return userDao.getLoggedInUser();
    }

    suspend fun getAllUser():List<User>?{
        return userDao.getAllUser();
    }

    suspend fun getUserByEmail(email:String):User?{
        return userDao.getUserByEmail(email);
    }

    suspend fun login(email:String):Boolean{
        return userDao.login(email)>0;
    }

    suspend fun logout(userId:Int):Boolean{
        return userDao.logoutUsers(userId)>0;
    }


    //User notes data provider

    suspend fun getAllNotes(userId: Int): LiveData<List<Note>> {
        return noteDao.getAllNotesOfUser(userId);
    }

    suspend fun insertNote(note:Note):Long{
        return noteDao.insertNote(note);
    }

    suspend fun deleteNote(note:Note){
        return noteDao.deleteNote(note);
    }


}