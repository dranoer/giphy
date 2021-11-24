package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.GiphyDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(
    private val webService: WebService
) {

    suspend fun getTrends(page: Int): Resource<List<GiphyDTO>> {
        return Resource.Success(webService.getTrends((page - 1) * 10, 10).giphyDTOS)
    }

    suspend fun search(name: String, page: Int): Resource<List<GiphyDTO>> {
        return Resource.Success(webService.search(name, (page - 1) * 10, 10).giphyDTOS)
    }
}