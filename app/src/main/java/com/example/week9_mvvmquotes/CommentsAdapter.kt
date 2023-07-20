package com.example.week9_mvvmquotes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.week9_mvvmquotes.databinding.CommentRowItemBinding
import com.example.week9_mvvmquotes.model.CommentsItem

class CommentsAdapter :
    ListAdapter<CommentsItem, CommentsAdapter.CommentViewHolder>(CommentDiffUtilCallback()) {

    private val iconBackgroundColors = listOf(
        R.color.icon_pink,
        R.color.icon_darkBlue,
        R.color.icon_deepBlue,
        R.color.icon_lightBlue,
        R.color.icon_tan
    )

    class CommentViewHolder(val binding: CommentRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentsItem>() {
        override fun areItemsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = ContextCompat.getSystemService(
            parent.context,
            LayoutInflater::class.java
        ) as LayoutInflater
        val binding = CommentRowItemBinding.inflate(inflater, parent, false)

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = getItem(position)

        val bgColor = ContextCompat.getColor(
            holder.itemView.context,
            iconBackgroundColors[position % iconBackgroundColors.size]
        )
        val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_commenter)
        holder.binding.imgComment.setImageDrawable(drawable)
        holder.binding.imgComment.setBackgroundColor(bgColor)
        holder.binding.textCommentName.text = item.name
        holder.binding.textCommentEmail.text = item.email
        holder.binding.textCommentBody.text = item.body


    }


}
