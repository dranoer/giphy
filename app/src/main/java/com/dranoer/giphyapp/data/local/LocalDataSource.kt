package com.dranoer.giphyapp.data.local

import com.dranoer.giphyapp.data.model.GiphyEntity
import com.dranoer.giphyapp.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val giphyDao: GiphyDao) {

    val giphies: Flow<List<GiphyEntity>> = giphyDao.getGiphies()
    val favorites: Flow<List<GiphyEntity>> = giphyDao.getFavorites(true)

    //    var searchResultsO: Flow<List<GiphyEntity>> = giphyDao.searchResult()
//    var searchResults: Flow<List<GiphyEntity>>

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

    suspend fun saveSearchResult(giphies: List<GiphyEntity>):  Flow<List<GiphyEntity>>{
//        giphyDao.searchResult(giphies)
//        searchResults = giphies
//        searchResults = giphies
//        giphyDao.saveGiphies(giphies)

//        searchResults =
//            giphies.map { searchResults }

        lateinit var n: Flow<List<GiphyEntity>>
        for (item in giphies) {
            n.apply { item }
        }

        return n
    }

    suspend fun getSearchResult(giphies: List<GiphyEntity>) {
//        searchResults.collect(giphies)
//            giphyDao.searchResult(name)
    }
}