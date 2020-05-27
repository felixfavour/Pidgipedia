package com.felixfavour.pidgipedia.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.Word
import com.felixfavour.pidgipedia.ui.dictionary.WordListAdapter
import com.felixfavour.pidgipedia.ui.home.HomeRecyclerViewAdapter
import com.felixfavour.pidgipedia.ui.home.UnapprovedWordListAdapter

@BindingAdapter("populateWordList")
fun populateWordList(recyclerView: RecyclerView, words: ArrayList<Word>) {
    val adapter = recyclerView.adapter as WordListAdapter
    adapter.submitList(words)
}
@BindingAdapter("populateEventstampsList")
fun populateEventstampList(recyclerView: RecyclerView, eventstamps: ArrayList<Eventstamp>) {
    val adapter = recyclerView.adapter as HomeRecyclerViewAdapter
    adapter.submitList(eventstamps)
}

@BindingAdapter("unapprovedWordsList")
fun unapprovedWordsList(recyclerView: RecyclerView, words: ArrayList<Word>) {
    val adapter = recyclerView.adapter as UnapprovedWordListAdapter
    adapter.submitList(words)
}