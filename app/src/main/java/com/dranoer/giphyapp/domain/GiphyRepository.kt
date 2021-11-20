package com.dranoer.giphyapp.domain

import com.dranoer.giphyapp.data.model.GiphyResponse
import com.dranoer.giphyapp.data.remote.SafeApiRequest
import com.dranoer.giphyapp.data.remote.WebService
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val webService: WebService
) : SafeApiRequest() {

    suspend fun getTrends(): GiphyResponse {
        return apiRequest { webService.getTrends() }
    }
}