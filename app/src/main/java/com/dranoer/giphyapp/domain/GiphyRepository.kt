package com.dranoer.giphyapp.domain

import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.remote.DataSource
import com.dranoer.giphyapp.data.remote.Resource
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val networkDataSource: DataSource,
) {

    suspend fun getTrending(): Resource<List<Giphy>> {
        return networkDataSource.getTrends()
    }

    suspend fun search(name: String): Resource<List<Giphy>> {
        return networkDataSource.search(name)
    }
}