package com.felixfavour.pidgipedia.ui.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.databinding.WordListItemBinding
import com.felixfavour.pidgipedia.entity.Word

class WordListAdapter(
    private val clickAction: OnWordClickListener
): ListAdapter<Word, WordListAdapter.WordListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }
    }


    class WordListViewHolder(
        val binding: WordListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            word: Word,
            clickAction: OnWordClickListener
        ) {
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.root.layoutParams = layoutParams
            binding.root.setOnClickListener {
                clickAction.onWordClick(word)
            }
            binding.word = word
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        return WordListViewHolder(WordListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, clickAction)
    }

    class OnWordClickListener(private val wordClickAction: (word: Word) -> Unit) {

        fun onWordClick(word: Word) {
            wordClickAction(word)
        }

    }

}
