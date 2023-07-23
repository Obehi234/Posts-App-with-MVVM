package com.example.week9_mvvmquotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week9_mvvmquotes.CommentsAdapter
import com.example.week9_mvvmquotes.R
import com.example.week9_mvvmquotes.addComment.database.CommentsItem
import com.example.week9_mvvmquotes.addComment.database.CommentsViewModel
import com.example.week9_mvvmquotes.databinding.ActivityCommentsBinding
import com.example.week9_mvvmquotes.viewmodel.PostViewModel

class CommentsActivity : AppCompatActivity() {
    private lateinit var commentViewModel: CommentsViewModel
    private var _binding: ActivityCommentsBinding? = null
    private val binding get() = _binding!!
    private val commentsAdapter by lazy { CommentsAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var overlay: View
    private lateinit var prgBar: ProgressBar
    private var postId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Comments"
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        commentViewModel.initializeDatabase(this)

        postId = intent.getIntExtra("postId", -1)
        if (postId != -1) {
            commentViewModel.fetchCommentsForPost(postId).observe(this, Observer { comments ->
                setUpCommentsRecyclerView(comments)
            })
        }
        prgBar = binding.progressHome
        overlay = binding.overlayView
        fragmentContainer = binding.fragmentContainer
        setUpFAButton()
    }

    private fun setUpCommentsRecyclerView(comments: List<CommentsItem>?) {
       prgBar.visibility = View.GONE
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
        overlay.visibility = View.VISIBLE
        overlay.alpha = 0f
        overlay.animate()
            .alpha(1f)
            .setDuration(500)
            .start()

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment_comment: Fragment = AddCommentFragment.newInstance(postId)
        fragmentTransaction.replace(R.id.fragmentContainer, fragment_comment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        fragmentContainer.visibility = View.VISIBLE

    }

    override fun onBackPressed() {
        if (fragmentContainer.visibility == View.VISIBLE) {
            overlay.animate()
                .alpha(0f)
                .setDuration(500)
                .withEndAction{
                    overlay.visibility = View.GONE
                }
                .start()

            fragmentContainer.visibility = View.GONE
        }
        super.onBackPressed()
    }


}