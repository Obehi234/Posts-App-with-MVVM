package com.example.week9_mvvmquotes.addComment.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9_mvvmquotes.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel: ViewModel() {
    private val repository: Repository = Repository
    private val _commentsLiveData = MutableLiveData<List<CommentsItem>>()
    val commentsLiveData: LiveData<List<CommentsItem>> get() = _commentsLiveData

    fun initializeDatabase(context: Context) {
        repository.initializeDatabase(context)
    }
    fun fetchCommentsForPost(postId: Int): LiveData<List<CommentsItem>> {
        if (_commentsLiveData.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val comments = repository.getComments(postId)
                    withContext(Dispatchers.Main) {
                        _commentsLiveData.postValue(comments)
                        Log.d("CHECK_RESPONSE_COMMENTS", "${comments.size} - $postId")
                    }
                } catch (e: Exception) {
                    Log.d("CHECK_RESPONSE_COMMENTS_FAILED", "${e.message}")
                }
            }
        }
        return commentsLiveData
    }

    suspend fun saveSingleCommentToDatabase(comment: CommentsItem) {
        viewModelScope.launch {
            try {
                repository.saveSingleCommentToDatabase(comment)
            } catch (e: Exception) {
                Log.d("CHECK_COMMENT_SAVED", "${e.printStackTrace()}")
            }

        }

    }
}
