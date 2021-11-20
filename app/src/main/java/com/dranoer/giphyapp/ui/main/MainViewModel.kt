package com.dranoer.giphyapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: GiphyRepository
) : ViewModel() {

    private var giphyList: List<Giphy>? = null

    private var _giphies = MutableLiveData<List<Giphy>>()
    val giphies: LiveData<List<Giphy>>
        get() = _giphies

    fun getTrends() {
        viewModelScope.launch {
            try {
                giphyList = repository.getTrends().giphies
                _giphies.value = giphyList!!
                Log.d("safeapi_request", "request succeed :)")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("safeapi_request", "request failed :( ${e.message}")
            }
        }
    }
}