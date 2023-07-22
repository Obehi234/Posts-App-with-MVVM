package com.example.week9_mvvmquotes.addComment.database

import androidx.room.*

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentsItem: CommentsItem)

    @Insert
    suspend fun insertComments(commentsItems: List<CommentsItem>)

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentsForPost(postId: Int) : List<CommentsItem>
}