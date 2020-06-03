package com.felixfavour.pidgipedia.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.databinding.UnapprovedWordItemBinding
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.view.OnWordClickListener

class UnapprovedWordListAdapter(
    private val onWordClickListener: OnWordClickListener
): ListAdapter<Word, UnapprovedWordListAdapter.UnapprovedWordViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }

    class UnapprovedWordViewHolder(val binding: UnapprovedWordItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            word: Word,
            onWordClickListener: OnWordClickListener
        ) {
            // propert-access syntax not used here because of conflict in iD names in layout
            binding.setWord(word)
            val marginLayoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.card.layoutParams = marginLayoutParams.apply {
                marginEnd = 24
            }
            binding.cardLayout.setOnClickListener {
                onWordClickListener.onWordClick(word, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnapprovedWordViewHolder {
        return UnapprovedWordViewHolder(UnapprovedWordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UnapprovedWordViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, onWordClickListener)
    }

}
