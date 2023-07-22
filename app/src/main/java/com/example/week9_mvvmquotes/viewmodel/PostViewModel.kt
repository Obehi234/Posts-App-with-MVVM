package com.example.week9_mvvmquotes.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9_mvvmquotes.addComment.database.CommentsItem
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
}



    //fun addComment(newComment: CommentsItem) {
    //    val currentComments = _commentsLiveData.value.orEmpty().toMutableList()
    //    currentComments.add(newComment)
    //    _commentsLiveData.postValue(currentComments)

     //   viewModelScope.launch(Dispatchers.IO) { repository.saveCommentsToDatabase(listOf(newComment))  }
   // }
//}

