package com.example.week9_mvvmquotes.addComment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentsForPost(postId: Int) : List<Comment>
}