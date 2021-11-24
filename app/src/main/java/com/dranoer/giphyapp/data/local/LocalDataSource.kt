package com.dranoer.giphyapp.data.local

import com.dranoer.giphyapp.data.mapper.mapToEntity
import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.model.GiphyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val giphyDao: GiphyDao) {

    val favorites: Flow<List<GiphyEntity>> = giphyDao.getFavorites(true)

    suspend fun saveGiphy(giphy: Giphy) {
        giphyDao.saveGiphy(giphy.mapToEntity())
    }

    suspend fun getFavStateGiphy(id: String): Boolean? {
        return giphyDao.getGiphy(id)
    }
}