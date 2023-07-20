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
import com.example.week9_mvvmquotes.R
import com.example.week9_mvvmquotes.databinding.FragmentAddCommentBinding

class AddCommentFragment : Fragment() {
    private lateinit var editTextComment: EditText
    private lateinit var btnAddComment: Button
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextComment = binding.commentEditText
        btnAddComment = binding.submitButton

        btnAddComment.setOnClickListener { addComment() }
    }

    private fun addComment() {
        val commentText = editTextComment.text.toString()
        if (commentText.isNotEmpty()) {
            Log.d("CHECK_ADD_TO_DATABASE", "$commentText")
            editTextComment.text.clear()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}