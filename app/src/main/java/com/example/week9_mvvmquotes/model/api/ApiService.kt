package com.example.week9_mvvmquotes.model.api

import com.example.week9_mvvmquotes.model.CommentsItem
import com.example.week9_mvvmquotes.model.PostListItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostListItem>

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId : Int) : List<CommentsItem>
}
