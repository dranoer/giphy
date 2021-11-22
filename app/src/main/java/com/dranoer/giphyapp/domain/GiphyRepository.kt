package com.dranoer.giphyapp.domain

import android.util.Log
import com.dranoer.giphyapp.data.local.LocalDataSource
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
        val giphiesList = ArrayList<GiphyEntity>()

        when (response) {
            is Resource.Success -> {
                for (item in response.data) {
                    val giphyEntity =
                        GiphyEntity(id = item.id, title = item.title!!, isFavorite = false)
                    giphiesList.add(giphyEntity)
                }
                localDataSource.saveGiphies(giphiesList)
            }
            else -> {
                Log.d("nazanin", "getting trends from networkDataSource has been failed :(")
            }
        }

        return response
    }

    suspend fun search(name: String): Resource<List<Giphy>> {
        return networkDataSource.search(name)
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