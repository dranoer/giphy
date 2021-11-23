package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.GiphyDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(
    private val webService: WebService
) {

    suspend fun getTrends(): Resource<List<GiphyDTO>> {
        return Resource.Success(webService.getTrends().giphyDTOS)
    }

    suspend fun search(name: String): Resource<List<GiphyDTO>> {
        return Resource.Success(webService.search(name).giphyDTOS)
    }
}