package com.felixfavour.pidgipedia.view.dictionary

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.felixfavour.pidgipedia.entity.Word


class SuggestionsAdapter(context: Context, layoutResId: Int, val wordList: List<Word>): ArrayAdapter<Word>(
    context, layoutResId, wordList
) {
    private val words =  mutableListOf<Word>()
    companion object {
        val suggestions = mutableListOf<Word>()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val word = getItem(position)

//        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.suggestions_item, parent, false)

        if (convertView != null) {
//            val layoutInflater = LayoutInflater.from(context).inflate(R.layout.suggestions_item, parent, false)
            val textView = convertView as TextView
            textView.text = "${word?.name}"
            return convertView
        } else {
            return super.getView(position, convertView, parent)
        }
    }

    override fun getFilter(): Filter {
        if (words.isEmpty()) {
            words.addAll(wordList)
        }
        return newFilter
    }

    private val newFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()

            val filterPattern = constraint.toString().toLowerCase().trim()
            words.forEach { word ->
                if (word.name.toLowerCase().contains(filterPattern)) {
                    if (!suggestions.contains(word)) {
                        suggestions.add(word)
                    }
                }
            }

            return  filterResults.apply {
                values = suggestions
                count = suggestions.size
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            resultValue as Word
            return resultValue.name
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            val stuff = results?.values as List<Word>?
            if (stuff != null) {
                clear()
                addAll(stuff)
            }
            notifyDataSetChanged()
        }
    }

}