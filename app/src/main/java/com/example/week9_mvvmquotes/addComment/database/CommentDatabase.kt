package com.example.week9_mvvmquotes.addComment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class CommentsDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao

    companion object {
        private var INSTANCE: CommentsDatabase? = null

        fun getDatabase(context: Context): CommentsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommentsDatabase::class.java,
                    "comment_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
