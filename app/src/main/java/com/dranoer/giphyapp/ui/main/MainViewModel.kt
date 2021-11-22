package com.dranoer.giphyapp.ui.main

import androidx.lifecycle.*
import com.dranoer.giphyapp.data.model.GiphyEntity
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@Inject constructor(
    var repository: GiphyRepository
) : ViewModel() {

    val allGiphies: LiveData<List<GiphyEntity>> = repository.getGiphies().asLiveData()
    val allFavorites: LiveData<List<GiphyEntity>> = repository.getFavorites().asLiveData()

    fun getTrends() {
        viewModelScope.launch {
            repository.getTrending()
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

    fun updateFavorite(id: String) {
        viewModelScope.launch {
            repository.updateGiphy(id)
        }
    }
}