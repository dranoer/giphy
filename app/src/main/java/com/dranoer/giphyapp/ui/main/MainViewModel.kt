package com.dranoer.giphyapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: GiphyRepository
) : ViewModel() {

    fun getTrends() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getTrending())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun search(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.search(name))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}