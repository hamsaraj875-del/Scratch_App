package io.example.notes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User):Long;

    @Delete
    suspend fun deleteUser(user:User);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email ")
    suspend fun updateUserPassword(email:String,newPassword:String):Int;

    @Query("UPDATE users SET name = :newName WHERE email = :email")
    suspend fun updateUserName(email:String,newName:String):Int;

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE id=:userId")
    suspend fun getUserById(userId:Long): User;
}