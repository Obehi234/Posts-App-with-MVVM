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
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.week9_mvvmquotes.databinding.PostRowItemBinding
import com.example.week9_mvvmquotes.model.PostListItem

class PostRecyclerAdapter(private val listener: OnItemClickListener) :
    ListAdapter<PostListItem, PostRecyclerAdapter.PostViewHolder>(PostDiffUtilCallback()) {

    interface OnItemClickListener {
        //fun onItemClick(item: PostListItem)
        fun onItemClick(postId: Int)
    }

    private val iconBackgroundColors = listOf(
        R.color.icon_pink,
        R.color.icon_darkBlue,
        R.color.icon_deepBlue,
        R.color.icon_lightBlue,
        R.color.icon_tan
    )

    class PostViewHolder(val binding: PostRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    class PostDiffUtilCallback : DiffUtil.ItemCallback<PostListItem>() {
        override fun areItemsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = ContextCompat.getSystemService(
            parent.context,
            LayoutInflater::class.java
        ) as LayoutInflater
        val binding = PostRowItemBinding.inflate(inflater, parent, false)

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        val icon = createIconWithFirstLetter(holder.itemView.context, item.title)
        val bgColor = ContextCompat.getColor(
            holder.itemView.context,
            iconBackgroundColors[position % iconBackgroundColors.size]
        )
        holder.binding.imgPost.setImageDrawable(icon)
        holder.binding.imgPost.setBackgroundColor(bgColor)
        holder.binding.textPostTitle.text = item.title
        holder.binding.textPostBody.text = item.body

        holder.itemView.setOnClickListener {
            listener.onItemClick(item.id)
        }
    }

    private fun createIconWithFirstLetter(context: Context?, title: String): Drawable? {
        val textView = TextView(context)
        val firstLetter = title.first()
        textView.text = firstLetter.toString()
        textView.textSize = 14f
        textView.setTextColor(ContextCompat.getColor(context!!, android.R.color.white))
        textView.gravity = Gravity.CENTER
        textView.setPadding(0, 2, 0, 0)

        val iconSize = 30.dpToPx(context)
        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        textView.layout(0, 0, iconSize, iconSize)

        val bitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        textView.layout(0, 0, canvas.width, canvas.height)
        textView.draw(canvas)

        return BitmapDrawable(context.resources, bitmap)

    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}