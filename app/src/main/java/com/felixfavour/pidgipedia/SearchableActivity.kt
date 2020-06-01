package com.felixfavour.pidgipedia

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.felixfavour.pidgipedia.databinding.ActivitySearchableBinding

class SearchableActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_searchable)
        setContentView(R.layout.activity_searchable)

        if (intent.action == Intent.ACTION_SEARCH) {
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            searchWord(searchQuery)
        }
    }

    private fun searchWord(searchQuery: String?) {
        TODO("Not yet implemented")
    }
}
