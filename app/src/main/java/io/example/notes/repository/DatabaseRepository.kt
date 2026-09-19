package io.example.notes.repository

import androidx.lifecycle.LiveData
import io.example.notes.room.User
import io.example.notes.room.UserDao

class DatabaseRepository(private val userDao: UserDao) {

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

}