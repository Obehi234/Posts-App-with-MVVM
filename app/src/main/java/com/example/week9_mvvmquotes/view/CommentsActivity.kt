package com.example.week9_mvvmquotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week9_mvvmquotes.CommentsAdapter
import com.example.week9_mvvmquotes.R
import com.example.week9_mvvmquotes.databinding.ActivityCommentsBinding
import com.example.week9_mvvmquotes.model.CommentsItem
import com.example.week9_mvvmquotes.viewmodel.PostViewModel

class CommentsActivity : AppCompatActivity() {
    private lateinit var postViewModel: PostViewModel
    private var _binding: ActivityCommentsBinding? = null
    private val binding get() = _binding!!
    private val commentsAdapter by lazy { CommentsAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fragmentContainer: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Comments"

        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        val postId = intent.getIntExtra("postId", -1)
        if (postId != -1) {
            postViewModel.fetchCommentsForPost(postId).observe(this, Observer { comments ->
                setUpCommentsRecyclerView(comments)
            })
        }
        fragmentContainer = binding.fragmentContainer
        setUpFAButton()
    }

    private fun setUpCommentsRecyclerView(comments: List<CommentsItem>?) {
        binding.commentsRecycler.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = commentsAdapter
        }
        commentsAdapter.submitList(comments)

    }

    private fun setUpFAButton() {
        binding.fabAddComment.setOnClickListener {
            showAddCommentFragment()
        }
    }

    private fun showAddCommentFragment() {
        if (fragmentContainer.visibility == View.VISIBLE) {
            return
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment_comment: Fragment = AddCommentFragment()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment_comment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        fragmentContainer.visibility = View.VISIBLE

    }

    override fun onBackPressed() {
        if (fragmentContainer.visibility == View.VISIBLE) {
            fragmentContainer.visibility = View.GONE
        }
        super.onBackPressed()
    }


}