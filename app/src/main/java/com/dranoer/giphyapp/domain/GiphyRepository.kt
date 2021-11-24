package com.dranoer.giphyapp.domain

import com.dranoer.giphyapp.data.local.LocalDataSource
import com.dranoer.giphyapp.data.mapper.mapToDomain
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.remote.NetworkDataSource
import com.dranoer.giphyapp.data.remote.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GiphyRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
) {

    suspend fun getTrending(page: Int): Resource<List<Giphy>> {
        val response = networkDataSource.getTrends(page)
        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data
                    .map { it.mapToDomain() }
                    .map { it.copy(isFavorite = getFavState(it.id)) })
            }
            is Resource.Failure -> {
                Resource.Failure(response.exception)
            }
        }
    }

    suspend fun getFavState(giphyId: String): Boolean {
        return localDataSource.getFavStateGiphy(giphyId) ?: false
    }

    suspend fun search(name: String, page: Int): Resource<List<Giphy>> {
        val response = networkDataSource.search(name, page)
        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data
                    .map { it.mapToDomain() }
                    .map { it.copy(isFavorite = getFavState(it.id)) })
            }
            is Resource.Failure -> {
                Resource.Failure(response.exception)
            }
        }
    }

    suspend fun updateFavGiphy(giphy: Giphy) {
        localDataSource.saveGiphy(giphy.copy(isFavorite = !giphy.isFavorite))
    }

    fun getFavorites(): Flow<List<Giphy>> {
        return localDataSource.favorites
            .map { gifList -> gifList.map { giphy -> giphy.mapToDomain() } }
    }
}