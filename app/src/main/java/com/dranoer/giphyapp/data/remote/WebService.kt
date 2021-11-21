package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("gifs/trending")
    suspend fun getTrends(): GiphyResponse

    @GET("gifs/search")
    suspend fun search(@Query("q") query: String): GiphyResponse
}