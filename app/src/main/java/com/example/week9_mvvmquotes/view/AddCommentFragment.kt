package com.example.week9_mvvmquotes.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.week9_mvvmquotes.addComment.database.CommentsItem
import com.example.week9_mvvmquotes.addComment.database.CommentsDatabase
import com.example.week9_mvvmquotes.addComment.database.CommentsViewModel
import com.example.week9_mvvmquotes.databinding.FragmentAddCommentBinding
import com.example.week9_mvvmquotes.viewmodel.PostViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCommentFragment() : Fragment() {
    private lateinit var editTextComment: EditText
    private lateinit var btnAddComment: Button
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var commentsDatabase: CommentsDatabase
    private lateinit var commentViewModel: CommentsViewModel
    private var postId: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commentViewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)

        editTextComment = binding.commentEditText
        btnAddComment = binding.submitButton
        commentsDatabase = CommentsDatabase.getDatabase(requireContext())

        btnAddComment.setOnClickListener { addCommentToDatabase()
        hideKeyboard()}
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun addCommentToDatabase() {
        val commentText = editTextComment.text.toString().trim()
        postId = arguments?.getInt(ARG_POST_ID, -1) ?: -1
        val commentsItem =
            CommentsItem(id = 0, postId = postId, name = "", email = "", body = commentText)
        Log.d("CHECK_ADD_TODATABASE", "$commentsItem - $postId")
        lifecycleScope.launch(Dispatchers.IO) { if (commentText.isNotEmpty() && postId != -1) {
            commentViewModel.saveSingleCommentToDatabase(commentsItem)
        } }
        editTextComment.text.clear()
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