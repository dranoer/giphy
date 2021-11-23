package com.dranoer.giphyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ContentState {
    object Loading : ContentState()
    object Data : ContentState()
    data class Error(val message: String) : ContentState()
}

@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@Inject constructor(
    var repository: GiphyRepository
) : ViewModel() {

    data class MainViewState(
        val layoutState: ContentState = ContentState.Loading,
        val pageLoading: Boolean = false,
        val searchTerms: String = "",
        val giphyList: List<Giphy> = listOf(),
        val favGiphyList: List<Giphy> = listOf(),
    )

    val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData(MainViewState())

    init {
        getTrends()
    }

    fun getTrends() {
        viewModelScope.launch {
            val result = repository.getTrending()
            when (result) {
                is Resource.Success -> {
                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        layoutState = ContentState.Data,
                        giphyList = result.data
                    )
                }
                is Resource.Failure -> {}
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            val result = repository.search(name)
            when (result) {
                is Resource.Success -> {
                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        searchTerms = name,
                        layoutState = ContentState.Data,
                        giphyList = result.data
                    )
                }
                is Resource.Failure -> {}
            }
        }
    }

    fun updateFavorite(id: String) {
        viewModelScope.launch {
            repository.updateGiphy(id)
        }
    }
}