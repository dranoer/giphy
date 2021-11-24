package com.dranoer.giphyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ContentState {
    object Loading : ContentState()
    object TrendsData : ContentState()
    object SearchData : ContentState()
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
        val pageNumber: Int = 1,
        val searchTerms: String = "",
        val giphyList: List<Giphy> = listOf(),
        val favGiphyList: List<Giphy> = listOf(),
    )

    val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData(MainViewState())

    init {
        getTrends()
        getFavorites()
    }


    fun getTrends() {
        getPaginationTrends(1)
    }

    fun search(terms: String) {
        getSearchPaginate(terms, 1)
    }


    fun onLoadMore() {
        val viewState = viewStateLiveData.value ?: return
        val page = viewState.pageNumber
        if (viewState.layoutState == ContentState.TrendsData) {
            getPaginationTrends(page)
        } else {
            getSearchPaginate(viewState.searchTerms, page)
        }
    }

    private fun getPaginationTrends(page: Int) {
        viewModelScope.launch {
            if (page == 1) {
                viewStateLiveData.value = viewStateLiveData.value?.copy(
                    layoutState = ContentState.Loading,
                    giphyList = listOf(),
                    pageLoading = true
                )
            } else {
                viewStateLiveData.value = viewStateLiveData.value?.copy(
                    pageLoading = true
                )
            }
            val result = repository.getTrending(page)
            when (result) {
                is Resource.Success -> {
                    val updatedList =
                        viewStateLiveData.value?.giphyList?.toMutableList() ?: mutableListOf()
                    updatedList.addAll(result.data)

                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        layoutState = ContentState.TrendsData,
                        giphyList = updatedList,
                        pageNumber = page + 1,
                        pageLoading = false
                    )
                }
                is Resource.Failure -> {
                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        layoutState = ContentState.Error(""),
                        giphyList = listOf(),
                        pageLoading = false
                    )
                }
            }
        }
    }

    private fun getSearchPaginate(name: String, page: Int) {
        viewModelScope.launch {
            if (page == 1) {
                viewStateLiveData.value = viewStateLiveData.value?.copy(
                    layoutState = ContentState.Loading,
                    giphyList = listOf(),
                    searchTerms = name,
                    pageLoading = true
                )
            } else {
                viewStateLiveData.value = viewStateLiveData.value?.copy(
                    pageLoading = true
                )
            }

            viewStateLiveData.value = viewStateLiveData.value?.copy(
                layoutState = ContentState.Loading,
                giphyList = listOf()
            )
            val result = repository.search(name, page)
            when (result) {
                is Resource.Success -> {
                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        layoutState = ContentState.SearchData,
                        giphyList = result.data,
                        pageNumber = page + 1,
                        pageLoading = false
                    )
                }
                is Resource.Failure -> {
                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        layoutState = ContentState.Error(""),
                        giphyList = listOf(),
                        pageLoading = false
                    )
                }
            }
        }
    }


    fun getFavorites() {
        viewModelScope.launch {
            repository.getFavorites()
                .collect { faveList ->

                    val updatedMainList = viewStateLiveData.value?.giphyList
                        ?.map {
                            val isFav =
                                faveList.find { fav -> fav.id == it.id }?.isFavorite ?: false
                            it.copy(isFavorite = isFav)
                        } ?: listOf()

                    viewStateLiveData.value = viewStateLiveData.value?.copy(
                        favGiphyList = faveList,
                        giphyList = updatedMainList
                    )
                }
        }
    }

    fun updateFavorite(giphy: Giphy) {
        viewModelScope.launch {
            repository.updateFavGiphy(giphy)
        }
    }
}