package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.GiphyResponse
import retrofit2.http.GET

interface WebService {

    @GET("gifs/trending")
    suspend fun getTrends(): GiphyResponse
}