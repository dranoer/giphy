package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.Giphy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DataSource @Inject constructor(
    private val webService: WebService
) {

    suspend fun getTrends(): Resource<List<Giphy>> {
        return Resource.Success(webService.getTrends().giphies)
    }
}