package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.data.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("gifs/trending")
    suspend fun getTrends(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): GiphyResponse

    @GET("gifs/search")
    suspend fun search(
        @Query("q") query: String, @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): GiphyResponse
}