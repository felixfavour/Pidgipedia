package com.felixfavour.pidgipedia.view.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager


/*
* Custom Layout manager to set make recyclerView unscrollable */
class CustomLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        val isScrollEnabled = false
        return super.canScrollVertically() && isScrollEnabled
    }
}