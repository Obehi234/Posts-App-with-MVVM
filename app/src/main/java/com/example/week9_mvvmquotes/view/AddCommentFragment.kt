package com.example.week9_mvvmquotes.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.week9_mvvmquotes.addComment.database.CommentsItem
import com.example.week9_mvvmquotes.addComment.database.CommentsDatabase
import com.example.week9_mvvmquotes.addComment.database.CommentsViewModel
import com.example.week9_mvvmquotes.databinding.FragmentAddCommentBinding
import com.example.week9_mvvmquotes.viewmodel.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCommentFragment() : Fragment() {
    private lateinit var editTextComment: EditText
    private lateinit var btnAddComment: Button
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var commentsDatabase: CommentsDatabase
    private lateinit var postViewModel: PostViewModel
    private lateinit var commentViewModel: CommentsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        postViewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        commentViewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)

        editTextComment = binding.commentEditText
        btnAddComment = binding.submitButton
        commentsDatabase = CommentsDatabase.getDatabase(requireContext())

        btnAddComment.setOnClickListener { addCommentToDatabase() }
    }

    private fun addCommentToDatabase() {
        val commentText = editTextComment.text.toString().trim()
        if (commentText.isNotEmpty()) {
            val commentsItem = CommentsItem(id = 0, postId = 0 ,name= "",email ="", body = commentText)
            Log.d("CHECK_ADD_TO_DATABASE", "$commentText - $commentsItem")
            lifecycleScope.launch (Dispatchers.IO){
                commentViewModel.saveSingleCommentToDatabase(commentsItem)
               // postViewModel.fetchCommentsForPost()

            }
            editTextComment.text.clear()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_POST_ID = "post_id"
        fun newInstance(postId: Int): AddCommentFragment {
            val fragment = AddCommentFragment()
            val args = Bundle()
            args.putInt(ARG_POST_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }

}