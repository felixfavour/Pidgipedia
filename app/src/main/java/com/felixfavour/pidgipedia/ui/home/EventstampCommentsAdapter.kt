package com.felixfavour.pidgipedia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.CommentLineItemBinding
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.ui.home.EventstampCommentsAdapter.EventstampCommentViewHolder

class EventstampCommentsAdapter:
    ListAdapter<Comment, EventstampCommentViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventstampCommentViewHolder {
        return EventstampCommentViewHolder(
            CommentLineItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: EventstampCommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    class EventstampCommentViewHolder(val binding: CommentLineItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment) {
            binding.comment = comment
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.root.layoutParams = layoutParams

            // Circle Image
            Glide.with(binding.root.context)
                .load(R.drawable.greta)
                .centerCrop()
                .circleCrop()
                .into(binding.authorImage)
            binding.executePendingBindings()
        }

    }

}
