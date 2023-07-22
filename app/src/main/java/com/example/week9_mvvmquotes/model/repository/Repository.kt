package com.example.week9_mvvmquotes.model.repository

import android.content.Context
import android.util.Log
import com.example.week9_mvvmquotes.addComment.database.CommentDao
import com.example.week9_mvvmquotes.addComment.database.CommentsItem
import com.example.week9_mvvmquotes.addComment.database.CommentsDatabase
import com.example.week9_mvvmquotes.model.PostListItem
import com.example.week9_mvvmquotes.model.api.ApiService
import com.example.week9_mvvmquotes.model.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Comment


object Repository {
    private val apiService: ApiService = RetrofitInstance.getInstance().create(ApiService::class.java)
    private lateinit var commentsDatabase: CommentsDatabase
    private lateinit var  getDao : CommentDao

    fun initializeDatabase(context: Context) {
        commentsDatabase = CommentsDatabase.getDatabase(context)
         getDao = commentsDatabase.commentDao()
    }

    suspend fun getPosts(): List<PostListItem> {
      return apiService.getPosts()
    }

    suspend fun getComments(postId: Int): List<CommentsItem> {
        Log.d("CHECK_GET_COMMENT", "IM IN GET COMMENTS, $postId")
        val remoteResponse = apiService.getComments(postId)
       // Log.d("CHECK_GET_REMOTE_RESPONSE", "$remoteResponse")
        withContext(Dispatchers.IO) {
            getDao.insertComments(remoteResponse)
        }
        val getCommentsFromDatabase = getDao.getCommentsForPost(postId)
        Log.d("CHECK_GET_DATABASE_COMMENT", "$getCommentsFromDatabase")

        return getCommentsFromDatabase
    }

    //suspend fun getAllCommentsFromDatabase(postId: Int) : List<CommentsItem>{
     //   return getDao.getCommentsForPost(postId)
   // }

    suspend fun saveSingleCommentToDatabase(comment: CommentsItem) {
        getDao.insertComment(comment)
    }

    //suspend fun saveCommentsToDatabase(comments: List<CommentsItem>) {
    //    val commentList = comments.map { commentsItemToComment(it) }
    //    withContext(Dispatchers.IO) {
     //       commentsDatabase.commentDao().insertComments(apiService.getComments(postId))
     //   }
   // }


}
