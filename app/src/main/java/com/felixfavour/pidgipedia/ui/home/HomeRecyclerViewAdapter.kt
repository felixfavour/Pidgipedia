package com.felixfavour.pidgipedia.ui.home

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.*
import com.felixfavour.pidgipedia.entity.Eventstamp
import kotlinx.android.synthetic.main.word_approval_item.view.*
import kotlinx.android.synthetic.main.word_approval_item.view.author_image
import kotlinx.android.synthetic.main.word_suggestion_item.view.*

/*
* Recycler View Adapter for Homepage with Multiple Types that have a supertype of Eventstamp*/
class HomeRecyclerViewAdapter(
    private val homeCardClickListener: HomeCardClickListener
): ListAdapter<Eventstamp, RecyclerView.ViewHolder>(Diffcallback) {

    companion object {
        const val TYPE_WORD_APPROVAL = 1
        const val TYPE_WORD_SUGGESTION = 2
        const val TYPE_RANK_REWARD = 3
        const val TYPE_BADGE_REWARD = 4
        const val TYPE_COMMENT_RESPONSE = 5
        const val TYPE_WORD_COMMENT = 6

        fun styleCard(cardView: CardView) {
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            cardView.layoutParams = layoutParams
            cardView.elevation = 5f
        }
    }

    object Diffcallback : DiffUtil.ItemCallback<Eventstamp>() {
        override fun areItemsTheSame(oldItem: Eventstamp, newItem: Eventstamp): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Eventstamp, newItem: Eventstamp): Boolean {
            return oldItem == newItem
        }

    }

    class WordApprovalViewHolder(val binding: WordApprovalItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            Glide.with(binding.root.context)
                .load(R.drawable.greta)
                .centerCrop()
                .circleCrop()
                .into(binding.authorImage)
            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }
    class WordSuggestionViewHolder(val binding: WordSuggestionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            Glide.with(binding.root.context)
                .load(R.drawable.greta)
                .centerCrop()
                .circleCrop()
                .into(binding.authorImage)
            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }
    class WordCommentViewHolder(val binding: WordCommentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            Glide.with(binding.root.context)
                .load(R.drawable.greta)
                .centerCrop()
                .circleCrop()
                .into(binding.authorImage)
            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }
    class RankRewardViewHolder(val binding: RankRewardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }
    class BadgeRewardViewHolder(val binding: BadgeRewardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }
    class CommentResponseViewHolder(val binding: CommentResponseItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (eventstamp: Eventstamp, homeCardClickListener: HomeCardClickListener) {
            binding.eventStamp = eventstamp
            binding.root.setOnClickListener {view ->
                homeCardClickListener.onHomeCardClick(view, eventstamp)
            }
            Glide.with(binding.root.context)
                .load(R.drawable.greta)
                .centerCrop()
                .circleCrop()
                .into(binding.authorImage)

            styleCard(binding.card)
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        when {
            item?.badgeRewardType != null -> {
                return TYPE_BADGE_REWARD
            }
            item?.rankRewardType != null -> {
                return TYPE_RANK_REWARD
            }
            item.isCommentResponse -> {
                return TYPE_COMMENT_RESPONSE
            }
            item.isWordComment -> {
                return TYPE_WORD_COMMENT
            }
            item.isApproved -> {
                return TYPE_WORD_APPROVAL
            }
            item.isSuggested -> {
                return TYPE_WORD_SUGGESTION
            }
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BADGE_REWARD -> BadgeRewardViewHolder(BadgeRewardItemBinding.inflate(LayoutInflater.from(parent.context)))
            TYPE_RANK_REWARD -> RankRewardViewHolder(RankRewardItemBinding.inflate(LayoutInflater.from(parent.context)))
            TYPE_COMMENT_RESPONSE -> CommentResponseViewHolder(CommentResponseItemBinding.inflate(LayoutInflater.from(parent.context)))
            TYPE_WORD_APPROVAL -> WordApprovalViewHolder(WordApprovalItemBinding.inflate(LayoutInflater.from(parent.context)))
            TYPE_WORD_SUGGESTION -> WordSuggestionViewHolder(WordSuggestionItemBinding.inflate(LayoutInflater.from(parent.context)))
            else -> WordCommentViewHolder(WordCommentItemBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            TYPE_BADGE_REWARD -> {
                val holderCasted = holder as BadgeRewardViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
            TYPE_RANK_REWARD -> {
                val holderCasted = holder as RankRewardViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
            TYPE_COMMENT_RESPONSE -> {
                val holderCasted = holder as CommentResponseViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
            TYPE_WORD_APPROVAL -> {
                val holderCasted = holder as WordApprovalViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
            TYPE_WORD_SUGGESTION -> {
                val holderCasted = holder as WordSuggestionViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
            TYPE_WORD_COMMENT -> {
                val holderCasted = holder as WordCommentViewHolder
                holderCasted.bind(item, homeCardClickListener)
            }
        }
    }

    class HomeCardClickListener(val clickAction: (view: View, eventstamp: Eventstamp) -> Unit) {
        fun onHomeCardClick(view: View, eventstamp: Eventstamp) {
            clickAction(view, eventstamp)
        }
    }

}
