package com.example.week9_mvvmquotes.model.repository

import com.example.week9_mvvmquotes.model.CommentsItem
import com.example.week9_mvvmquotes.model.PostListItem
import com.example.week9_mvvmquotes.model.api.ApiService
import com.example.week9_mvvmquotes.model.api.RetrofitInstance


object Repository {
    private val apiService: ApiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    suspend fun getPosts(): List<PostListItem> {
      return apiService.getPosts()
    }

    suspend fun getComments(postId: Int) : List<CommentsItem> {
        return apiService.getComments(postId)
    }
}
