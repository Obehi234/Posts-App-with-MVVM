package com.example.week9_mvvmquotes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9_mvvmquotes.model.CommentsItem
import com.example.week9_mvvmquotes.model.PostListItem
import com.example.week9_mvvmquotes.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostViewModel : ViewModel() {

    private val repository: Repository = Repository
    private var _post = MutableLiveData<List<PostListItem>>()
    val post: LiveData<List<PostListItem>>
        get() = _post

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = repository.getPosts()
                _post.value = posts
                Log.d("CHECK_RESPONSE_POST", "${posts.size}")
            } catch (e: Exception) {
                Log.d("CHECK_RESPONSE_POST", "${e.printStackTrace()}")
            }
        }
    }

    fun fetchCommentsForPost(postId: Int): LiveData<List<CommentsItem>> {
        val commentsLiveData = MutableLiveData<List<CommentsItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val comments = repository.getComments(postId)
                withContext(Dispatchers.Main) {
                    commentsLiveData.value = comments
                    Log.d("CHECK_RESPONSE_COMMENTS", "${comments.size} - $postId")
                }
            } catch (e: Exception) {
                Log.d("CHECK_RESPONSE_COMMENTS", "${e.printStackTrace()}")
            }
        }
        return commentsLiveData
    }
}