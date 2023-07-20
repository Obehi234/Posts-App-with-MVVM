package com.example.week9_mvvmquotes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week9_mvvmquotes.PostRecyclerAdapter
import com.example.week9_mvvmquotes.databinding.ActivityMainBinding
import com.example.week9_mvvmquotes.viewmodel.PostViewModel

class MainActivity : AppCompatActivity(), PostRecyclerAdapter.OnItemClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel

    private val postRecyclerAdapter by lazy {
        PostRecyclerAdapter(this)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.quoteRecycler.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = postRecyclerAdapter
        }

        postViewModel.fetchPosts()
        postViewModel.post.observe(this, Observer {
            postRecyclerAdapter.submitList(it)

        })

    }

    override fun onItemClick(postId: Int) {
        val intent = Intent(this, CommentsActivity::class.java)
        intent.putExtra("postId", postId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}