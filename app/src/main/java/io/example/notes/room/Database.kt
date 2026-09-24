package io.example.notes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = INSTANCE;
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"User").build();
                }

                INSTANCE = instance;
                return instance;
            }
        }
    }
}