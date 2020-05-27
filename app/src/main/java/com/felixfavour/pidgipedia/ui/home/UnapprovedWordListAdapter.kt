package com.felixfavour.pidgipedia.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.databinding.WordApprovalItemBinding
import com.felixfavour.pidgipedia.entity.Word

class UnapprovedWordListAdapter(): ListAdapter<Word, UnapprovedWordListAdapter.UnapprovedWordViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }

    class UnapprovedWordViewHolder(val binding: WordApprovalItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnapprovedWordViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UnapprovedWordViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}
