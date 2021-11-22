package com.dranoer.giphyapp.data.local

import com.dranoer.giphyapp.data.model.GiphyEntity
import com.dranoer.giphyapp.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val giphyDao: GiphyDao) {

    val giphies: Flow<List<GiphyEntity>> = giphyDao.getGiphies()

    suspend fun saveGiphies(giphies: List<GiphyEntity>) {
        giphyDao.saveGiphies(giphies)
    }

    suspend fun getCashedGiphies(): Resource<Flow<List<GiphyEntity>>> {
        return Resource.Success(giphyDao.getGiphies())
    }

    suspend fun getGiphy(id: String): Boolean {
        return giphyDao.getGiphy(id)
    }

    suspend fun updateGiphy(id: String, n: Boolean) {
        giphyDao.updateGiphy(id, n)
    }
}