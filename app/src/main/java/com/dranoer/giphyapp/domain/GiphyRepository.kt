package com.dranoer.giphyapp.domain

import com.dranoer.giphyapp.data.local.LocalDataSource
import com.dranoer.giphyapp.data.mapper.mapToEntity
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.model.GiphyEntity
import com.dranoer.giphyapp.data.remote.NetworkDataSource
import com.dranoer.giphyapp.data.remote.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GiphyRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
) {

    suspend fun getTrending(): Resource<List<Giphy>> {
        val response = networkDataSource.getTrends()
        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data.map { it.mapToEntity() })
            }
            is Resource.Failure -> {
                Resource.Failure(response.exception)
            }
        }
    }

    suspend fun search(name: String): Resource<List<Giphy>> {
        val response = networkDataSource.search(name)
        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data.map { it.mapToEntity() })
            }
            is Resource.Failure -> {
                Resource.Failure(response.exception)
            }
        }
    }

    fun getGiphies(): Flow<List<GiphyEntity>> {
        return localDataSource.giphies
    }

    suspend fun updateGiphy(id: String) {
        val isFavorite: Boolean = localDataSource.getGiphy(id)
        localDataSource.updateGiphy(id, !isFavorite)
    }

    fun getFavorites(): Flow<List<GiphyEntity>> {
        return localDataSource.favorites
    }
}