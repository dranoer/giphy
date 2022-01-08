package com.dranoer.giphyapp.ui

import android.widget.SearchView

inline fun SearchView.onQueryTextChanged(
    crossinline onQueryTextChanged: (String) -> Unit,
    crossinline onTextCleared: () -> Unit
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onQueryTextChanged(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText.isNullOrEmpty()) {
                onTextCleared()
            }
            return false
        }
    })
}