package com.example.week9_mvvmquotes.addComment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
   @PrimaryKey(autoGenerate = true)
   val id: Long = 0 ,
   val text: String,
)
