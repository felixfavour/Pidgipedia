package com.felixfavour.pidgipedia.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.databinding.CommentLineItemBinding
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.view.home.EventstampCommentsAdapter.EventstampCommentViewHolder

class EventstampCommentsAdapter(private val commentClickListener: CommentClickListener):
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
            CommentLineItemBinding.inflate(LayoutInflater.from(parent.context)), commentClickListener
        )
    }

    override fun onBindViewHolder(holder: EventstampCommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    class EventstampCommentViewHolder(
        val binding: CommentLineItemBinding,
        private val commentClickListener: CommentClickListener
    ):
        RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment) {
            binding.comment = comment
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.root.layoutParams = layoutParams

            // SET CLICK LISTENERS
            binding.authorImage.setOnClickListener { commentClickListener.onProfileClick(it, comment) }
            binding.deleteComment.setOnClickListener { commentClickListener.onDeleteClick(it, comment) }
            binding.editComment.setOnClickListener { commentClickListener.onEditClick(it, comment) }
            binding.reply.setOnClickListener { commentClickListener.onReplyClick(it, comment) }


            binding.executePendingBindings()
        }

    }

}

interface CommentClickListener {
    fun onReplyClick(view: View, comment: Comment)
    fun onEditClick(view: View, comment: Comment)
    fun onDeleteClick(view: View, comment: Comment)
    fun onProfileClick(view: View, comment: Comment)
}
