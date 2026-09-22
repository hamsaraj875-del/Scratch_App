package io.example.notes.repository

import io.example.notes.room.Note
import io.example.notes.room.NoteDao
import io.example.notes.room.User
import io.example.notes.room.UserDao

class DatabaseRepository(private val userDao: UserDao, private val noteDao: NoteDao) {



    //Users Data Provider
    suspend fun insert(user: User):Long{
        return userDao.insertUser(user);
    }

    suspend fun delete(user:User){
        return userDao.deleteUser(user);
    }

    suspend fun getUserById(userId:Long): User {
        return userDao.getUserById(userId);
    }

    suspend fun updatePassword(email:String ,newPassword:String):Int{
        return userDao.updateUserPassword(email,newPassword);
    }
    suspend fun updateName(email:String,newName:String):Int{
        return userDao.updateUserName(email,newName);
    }

    suspend fun getLoggedInUser():User?{
        return userDao.getLoggedInUser();
    }

    suspend fun getUserByEmail(email:String):User?{
        return userDao.getUserByEmail(email);
    }

    suspend fun logout(userId:Long):Boolean{
        return userDao.logoutUsers(userId)>0;
    }


    //User notes data provider

    suspend fun getAllNotes(userId: Int):List<Note>{
        return noteDao.getAllNotesOfUser(userId);
    }

    suspend fun insertNote(note:Note):Long{
        return noteDao.insertNote(note);
    }

}