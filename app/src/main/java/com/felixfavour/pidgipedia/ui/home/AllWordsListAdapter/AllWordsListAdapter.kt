package com.felixfavour.pidgipedia.ui.home.AllWordsListAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.felixfavour.pidgipedia.entity.Word

class AllWordsListAdapter(
    context: Context,
    layout: Int,
    items: ArrayList<Word>
) : ArrayAdapter<Word>(context, layout, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

}
