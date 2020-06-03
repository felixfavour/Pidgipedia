package com.felixfavour.pidgipedia.view.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.databinding.WordListItemBinding
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.view.OnWordClickListener

class WordListAdapter(
    private val clickAction: OnWordClickListener
): ListAdapter<Word, WordListAdapter.WordListViewHolder>(DiffCallback), SectionIndexer {
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
                clickAction.onWordClick(word, it)
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

    override fun getSections(): Array<Any> {
        TODO("Not yet implemented")
    }

    override fun getSectionForPosition(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getPositionForSection(p0: Int): Int {
        TODO("Not yet implemented")
    }

}
